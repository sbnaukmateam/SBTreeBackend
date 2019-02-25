package org.sbteam.sbtree.api;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;

import org.sbteam.sbtree.security.BasicAuthenticator;
import org.sbteam.sbtree.db.pojo.PingResult;

@Api(
    name = "auth",
    version = "v1"
)
public class Auth {

  @ApiMethod(name = "verify", httpMethod = "GET", authenticators = { BasicAuthenticator.class })
  public PingResult verify(User user) throws UnauthorizedException {
    if (user == null) {
        throw new UnauthorizedException("You are not logged in");
    }
    return new PingResult("OK");
  }
}
