package com.sso_server;

import java.util.UUID;

public class Tool {

  public static String getToken(){
    return UUID.randomUUID().toString().replace("-", "");
  }
}
