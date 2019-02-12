package org.sbteam.sbtree.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import org.sbteam.sbtree.db.pojo.PingResult;

@Api(
    name = "ping",
    version = "v1"
)
public class Ping {

  @ApiMethod(name = "ping", httpMethod = "GET")
  public PingResult ping() {
    return new PingResult("OK");
  }
}
