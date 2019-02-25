package org.sbteam.sbtree.security;

import javax.servlet.http.HttpServletRequest;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.sbteam.sbtree.Constants;;

public class BasicAuthenticator implements Authenticator {

    @Override
    public User authenticate(HttpServletRequest request) throws ServiceException {
        String token = request.getHeader(Constants.HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                String username = JWT.require(Algorithm.HMAC512(SecretHolder.getSecret(Constants.JWT_SECRET).getBytes()))
                    .build()
                    .verify(token.replace(Constants.TOKEN_PREFIX, ""))
                    .getSubject();
                if (username != null) {
                    return new User(username);
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