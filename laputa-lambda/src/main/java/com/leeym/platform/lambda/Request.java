package com.leeym.platform.lambda;

import java.util.Collections;
import java.util.Map;

public class Request {

  private String resource;
  private String path;
  private String httpMethod;
  private Map<String, String> headers;
  private Map<String, String> queryStringParameters;
  private Map<String, String> pathParameters;
  private Map<String, String> stageVariables;
  private RequestContext requestContext;
  private String body;
  private Boolean isBase64Encoded;

  public Request() {
  }

  public Request(String body) {
    this.body = body;
    this.headers = Collections.emptyMap();
  }

  public String getBody() {
    return body;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public Boolean getBase64Encoded() {
    return isBase64Encoded;
  }

  public Map<String, String> getStageVariables() {
    return stageVariables;
  }

  public Map<String, String> getPathParameters() {
    return pathParameters;
  }

  public Map<String, String> getQueryStringParameters() {
    return queryStringParameters;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public String getPath() {
    return path;
  }

  public String getResource() {
    return resource;
  }

  public RequestContext getRequestContext() {
    return requestContext;
  }

  public void setBase64Encoded(Boolean base64Encoded) {
    isBase64Encoded = base64Encoded;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public void setStageVariables(Map<String, String> stageVariables) {
    this.stageVariables = stageVariables;
  }

  public void setPathParameters(Map<String, String> pathParameters) {
    this.pathParameters = pathParameters;
  }

  public void setQueryStringParameters(Map<String, String> queryStringParameters) {
    this.queryStringParameters = queryStringParameters;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public void setRequestContext(RequestContext requestContext) {
    this.requestContext = requestContext;
  }

  public static class RequestContext {
    private String accountId;
    private String resourceId;
    private String stage;
    private String requestId;
    private Identity identity;
    private String resourcePath;
    private String httpMethod;
    private String apiId;

    public String getHttpMethod() {
      return httpMethod;
    }

    public Identity getIdentity() {
      return identity;
    }

    public String getAccountId() {
      return accountId;
    }

    public String getApiId() {
      return apiId;
    }

    public String getRequestId() {
      return requestId;
    }

    public String getResourceId() {
      return resourceId;
    }

    public String getResourcePath() {
      return resourcePath;
    }

    public String getStage() {
      return stage;
    }

    public void setHttpMethod(String httpMethod) {
      this.httpMethod = httpMethod;
    }

    public void setAccountId(String accountId) {
      this.accountId = accountId;
    }

    public void setApiId(String apiId) {
      this.apiId = apiId;
    }

    public void setIdentity(Identity identity) {
      this.identity = identity;
    }

    public void setRequestId(String requestId) {
      this.requestId = requestId;
    }

    public void setResourceId(String resourceId) {
      this.resourceId = resourceId;
    }

    public void setResourcePath(String resourcePath) {
      this.resourcePath = resourcePath;
    }

    public void setStage(String stage) {
      this.stage = stage;
    }

  }

  public static class Identity {
    private String cognitoIdentityPoolId;
    private String accountId;
    private String cognitoIdentityId;
    private String caller;
    private String apiKey;
    private String sourceIp;
    private String cognitoAuthenticationType;
    private String cognitoAuthenticationProvider;
    private String userArn;
    private String userAgent;
    private String user;

    public String getAccountId() {
      return accountId;
    }

    public String getApiKey() {
      return apiKey;
    }

    public String getCaller() {
      return caller;
    }

    public String getCognitoAuthenticationProvider() {
      return cognitoAuthenticationProvider;
    }

    public String getCognitoAuthenticationType() {
      return cognitoAuthenticationType;
    }

    public String getCognitoIdentityId() {
      return cognitoIdentityId;
    }

    public String getCognitoIdentityPoolId() {
      return cognitoIdentityPoolId;
    }

    public String getSourceIp() {
      return sourceIp;
    }

    public String getUser() {
      return user;
    }

    public String getUserAgent() {
      return userAgent;
    }

    public String getUserArn() {
      return userArn;
    }

    public void setAccountId(String accountId) {
      this.accountId = accountId;
    }

    public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
    }

    public void setCaller(String caller) {
      this.caller = caller;
    }

    public void setCognitoAuthenticationProvider(String cognitoAuthenticationProvider) {
      this.cognitoAuthenticationProvider = cognitoAuthenticationProvider;
    }

    public void setCognitoAuthenticationType(String cognitoAuthenticationType) {
      this.cognitoAuthenticationType = cognitoAuthenticationType;
    }

    public void setCognitoIdentityId(String cognitoIdentityId) {
      this.cognitoIdentityId = cognitoIdentityId;
    }

    public void setCognitoIdentityPoolId(String cognitoIdentityPoolId) {
      this.cognitoIdentityPoolId = cognitoIdentityPoolId;
    }

    public void setSourceIp(String sourceIp) {
      this.sourceIp = sourceIp;
    }

    public void setUser(String user) {
      this.user = user;
    }

    public void setUserAgent(String userAgent) {
      this.userAgent = userAgent;
    }

    public void setUserArn(String userArn) {
      this.userArn = userArn;
    }
  }
}
