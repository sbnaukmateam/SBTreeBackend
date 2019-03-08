package org.sbteam.sbtree.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import org.sbteam.sbtree.db.pojo.ResultWrapper;

@Api(name = "ping", version = "v1")
public class Ping {

  @ApiMethod(name = "ping", httpMethod = "GET")
  public ResultWrapper<String> ping() {
    return new ResultWrapper<>("OK");
  }
}
