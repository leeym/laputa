package com.leeym.platform.lambda;

import java.util.Collections;
import java.util.Map;

public class Request {

  String resource;
  String path;
  String httpMethod;
  Map<String, String> headers;
  Map<String, String> queryStringParameters;
  Map<String, String> pathParameters;
  Map<String, String> stageVariables;
  RequestContext requestContext;
  String body;
  Boolean isBase64Encoded;

  public Request() {
  }

  public Request(String body) {
    this.body = body;
    this.headers = Collections.emptyMap();
  }

  public static class RequestContext {
    String accountId;
    String resourceId;
    String stage;
    String requestId;
    Identity identity;
    String resourcePath;
    String httpMethod;
    String apiId;
  }

  public static class Identity {
    String cognitoIdentityPoolId;
    String accountId;
    String cognitoIdentityId;
    String caller;
    String apiKey;
    String sourceIp;
    String cognitoAuthenticationType;
    String cognitoAuthenticationProvider;
    String userArn;
    String userAgent;
    String user;
  }
}
