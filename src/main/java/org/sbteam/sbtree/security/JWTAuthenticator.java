package org.sbteam.sbtree.security;

import javax.servlet.http.HttpServletRequest;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.sbteam.sbtree.Constants;;

public class JWTAuthenticator implements Authenticator {

    @Override
    public User authenticate(HttpServletRequest request) throws ServiceException {
        String token = request.getHeader(Constants.HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                byte[] secret = SecretHolder.getSecret(Constants.JWT_SECRET).getBytes();
                Algorithm algorithm = Algorithm.HMAC512(secret);
                String userId = JWT.require(algorithm)
                    .build()
                    .verify(token.replace(Constants.TOKEN_PREFIX, ""))
                    .getSubject();
                if (userId != null) {
                    return new User(userId, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
        return null;
    }
}