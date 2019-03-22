package org.sbteam.sbtree.api;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;

import org.sbteam.sbtree.security.JWTAuthenticator;
import org.sbteam.sbtree.security.JWTTokenManager;
import org.sbteam.sbtree.db.pojo.LoginResult;
import org.sbteam.sbtree.db.pojo.ResultWrapper;
import org.sbteam.sbtree.db.pojo.SBUser;
import org.sbteam.sbtree.db.pojo.UsernamePasswordCredentials;
import org.sbteam.sbtree.security.SecurityUtils;

import static org.sbteam.sbtree.service.OfyService.ofy;

import java.security.NoSuchAlgorithmException;

@Api(name = "auth", version = "v1")
public class Auth {
    private JWTTokenManager tokenManager = new JWTTokenManager();

    @ApiMethod(name = "verify", httpMethod = "GET", authenticators = { JWTAuthenticator.class })
    public ResultWrapper<SBUser> verify(User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("You are not logged in");
        }
        SBUser result = ofy().load().type(SBUser.class).id(Long.valueOf(user.getId())).now();
        if (result == null) {
            throw new UnauthorizedException("You are not logged in");
        }
        return new ResultWrapper<>(result);
    }

    @ApiMethod(name = "login", httpMethod = "POST")
    public ResultWrapper<LoginResult> login(UsernamePasswordCredentials credentials)
            throws UnauthorizedException, NoSuchAlgorithmException, InternalServerErrorException {

        SBUser user = ofy().load().type(SBUser.class).filter("username", credentials.getUsername()).first().now();
        if (user == null || credentials.getPassword() == null) {
            throw new UnauthorizedException("Invalid credentials");
        }
        if (!SecurityUtils.sha1(credentials.getPassword()).equals(user.getHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        try {
            String token = tokenManager.createToken(user);
            LoginResult loginResult = new LoginResult("Login successful", token, user);
            return new ResultWrapper<>(loginResult);
        } catch (JWTCreationException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("JWT creation failed");
        }
    }

    @ApiMethod(name = "signup", httpMethod = "POST")
    public ResultWrapper<SBUser> signup(SBUser newUser)
            throws ConflictException, NoSuchAlgorithmException, BadRequestException {

        SBUser duplicateUser = ofy().load().type(SBUser.class).filter("username == ", newUser.getUsername()).first().now();

        if (duplicateUser != null) {
            throw new ConflictException("User already exists");
        }

        if (newUser.getPassword() == null) {
            throw new BadRequestException("Password missing!");
        }

        if (newUser.getUsername() == null) {
            throw new BadRequestException("Username missing!");
        }

        if (newUser.getName() == null) {
            throw new BadRequestException("Name missing!");
        }

        SBUser result = ofy().transact(() -> {
            try {
                if (newUser.getPatronId() != null) {
                    checkExists(newUser.getPatronId());
                }
                ofy().save().entity(newUser).now();
                return newUser;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return new ResultWrapper<>(result);
    }

    private SBUser checkExists(Long id) throws NotFoundException {
        try {
            return ofy().load().type(SBUser.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Person with ID: " + id);
        }
    }
}