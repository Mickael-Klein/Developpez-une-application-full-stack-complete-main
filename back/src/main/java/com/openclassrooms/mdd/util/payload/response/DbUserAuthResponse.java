package com.openclassrooms.mdd.util.payload.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DbUserAuthResponse {

  private static final String MESSAGE_TITLE = "message";
  private static final String REGISTERING_BAD_CREDENTIALS =
    "Bad credentials for registering";
  private static final String REGISTERING_EMAIL_ALREADY_USE =
    "Email is already registered";
  private static final String REGISTERING_USERNAME_ALREADY_TAKEN =
    "Username is already registered";
  private static final String LOGIN_BAD_CREDENTIALS =
    "Bad credentials for login";
  private static final String LOGIN_ERROR_LOGIN_USER =
    "An error occurred trying to connect user";
  private static final String JWT_INVALID_JWT = "Invalid jwt";
  private static final String TOKEN = "token";
  private static final String USER_INVALID_ID_PARAMETER =
    "Invalid user id parameter";
  private static final String USER_ERROR_OCCUR =
    "An error occurred trying to get user";

  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(MESSAGE_TITLE, message);
    return responseBody;
  }

  public Map<String, String> getRegisteringBadCredentialsResponseMessage() {
    return createResponseMessage(REGISTERING_BAD_CREDENTIALS);
  }

  public Map<String, String> getRegisteringEmailAlreadyUsedResponseMessage() {
    return createResponseMessage(REGISTERING_EMAIL_ALREADY_USE);
  }

  public Map<String, String> getRegisterUserameAlreadyUsedResponseMessage() {
    return createResponseMessage(REGISTERING_USERNAME_ALREADY_TAKEN);
  }

  public Map<String, String> getLoginBadCredentialsResponseMessage() {
    return createResponseMessage(LOGIN_BAD_CREDENTIALS);
  }

  public Map<String, String> getLoginErrorResponseMessage() {
    return createResponseMessage(LOGIN_ERROR_LOGIN_USER);
  }

  public Map<String, String> getJwtInvalidJwtResponseMessage() {
    return createResponseMessage(JWT_INVALID_JWT);
  }

  public Map<String, String> getJwtTokenResponseMessage(String jwtToken) {
    Map<String, String> jwtTokenResponse = new HashMap<>();
    jwtTokenResponse.put(TOKEN, jwtToken);
    return jwtTokenResponse;
  }

  public Map<String, String> getUserInvalidIdParameterResponseMessage() {
    return createResponseMessage(USER_INVALID_ID_PARAMETER);
  }

  public Map<String, String> getUserErrorOccurResponseMessage() {
    return createResponseMessage(USER_ERROR_OCCUR);
  }
}
