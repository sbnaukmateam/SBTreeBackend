package org.sbteam.sbtree.security;

import javax.servlet.http.HttpServletRequest;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;

import org.sbteam.sbtree.Constants;
import org.sbteam.sbtree.db.pojo.SBUser;

public class JWTAuthenticator implements Authenticator {

    @Override
    public User authenticate(HttpServletRequest request) throws ServiceException {
        String token = request.getHeader(Constants.HEADER_STRING);
        if (token == null) {
            return null;
        }
        String parsedToken = token.replace(Constants.TOKEN_PREFIX, "");
        try {
            JWTTokenManager manager = new JWTTokenManager();
            SBUser user = manager.verifyToken(parsedToken);
            if (user == null) {
                return null;
            }
            return new User(user.getId().toString(), user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}