package org.sbteam.sbtree.api;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.googlecode.objectify.Key;

import org.sbteam.sbtree.security.JWTAuthenticator;
import org.sbteam.sbtree.security.JWTTokenGenerator;
import org.sbteam.sbtree.security.SecretHolder;
import org.sbteam.sbtree.Constants;
import org.sbteam.sbtree.db.pojo.LoginResult;
import org.sbteam.sbtree.db.pojo.ResultWrapper;
import org.sbteam.sbtree.db.pojo.SBUser;
import org.sbteam.sbtree.db.pojo.UsernamePasswordCredentials;
import org.sbteam.sbtree.security.SecurityUtils;

import static org.sbteam.sbtree.service.OfyService.ofy;

import java.security.NoSuchAlgorithmException;


@Api(name = "auth", version = "v1")
public class Auth {

    @ApiMethod(name = "verify", httpMethod = "GET", authenticators = { JWTAuthenticator.class })
    public ResultWrapper verify(User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("You are not logged in");
        }
        return new ResultWrapper("OK");
    }

    @ApiMethod(name = "login", httpMethod = "POST")
    public ResultWrapper<String> login(UsernamePasswordCredentials credentials)
      throws UnauthorizedException, NoSuchAlgorithmException, InternalServerErrorException {

        SBUser result = ofy().load().type(SBUser.class).filter("login == ", credentials.getUsername()).first().now();

        if (result == null || credentials.getPassword() == null)  {
            throw new UnauthorizedException("Invalid credentials");
        }
        if (!SecurityUtils.sha1(credentials.getPassword()).equals(result.getPassword())){
            throw new UnauthorizedException("Invalid credentials");
        }
        try {
            JWTTokenGenerator generator = new JWTTokenGenerator();
            String token = generator.createToken(result.getId());
            return new ResultWrapper<>("Login successful", token);
        } catch (JWTCreationException e){
            e.printStackTrace();
            throw new InternalServerErrorException("JWT creation failed");
        }
    }

    @ApiMethod(name = "signup", httpMethod = "POST")
    public ResultWrapper<String> register(UsernamePasswordCredentials credentials) throws ConflictException, NoSuchAlgorithmException {

        SBUser result = ofy().load().type(SBUser.class).filter("login == ", credentials.getUsername()).first().now();

        if (result != null) {
            throw new ConflictException("User already exists");
        }

        String hash = SecurityUtils.sha1(credentials.getPassword());
        SBUser user = new SBUser();
        user.setLogin(credentials.getUsername());
        user.setPassword(hash);

        ofy().save().entity(user).now();

        return new ResultWrapper<>("User created");
    }
}