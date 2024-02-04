package com.openclassrooms.mdd.util.payload.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DbUserResponse {

  private static final String MESSAGE_TITLE = "message";
  private static final String INVALID_JWT_TOKEN_ID =
    "no user found with the jwt token id";
  private static final String INVALID_UPDATE_PROFIL_REQUEST_PAYLOAD =
    "bad credentials for updating profil";
  private static final String INVALID_SUBJECT_ID_PARAMETER =
    "the id parameter provided doesn't match any existing subject";
  private static final String INVALID_TYPE_SUBJECT_ID_PARAMETER =
    "the id parameter provided is not a number";
  private static final String USER_ALREADY_SUB_OF_THIS_SUBJECT =
    "user already subscribed to this subject";
  private static final String USER_NOT_SUB_OF_THIS_SUBJECT =
    "user is not a subscriber of this subject";

  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(MESSAGE_TITLE, message);
    return responseBody;
  }

  public Map<String, String> getMeNoUserFoundWithThisJwtTokenId() {
    return createResponseMessage(INVALID_JWT_TOKEN_ID);
  }

  public Map<String, String> putUpdateProfilInvalidCredentials() {
    return createResponseMessage(INVALID_UPDATE_PROFIL_REQUEST_PAYLOAD);
  }

  public Map<String, String> subscriptionInvalidParameterSubjectIdType() {
    return createResponseMessage(INVALID_TYPE_SUBJECT_ID_PARAMETER);
  }

  public Map<String, String> subscriptionInvalidParameterSubjectId() {
    return createResponseMessage(INVALID_SUBJECT_ID_PARAMETER);
  }

  public Map<String, String> subscriptionUserAlreadySubscribed() {
    return createResponseMessage(USER_ALREADY_SUB_OF_THIS_SUBJECT);
  }

  public Map<String, String> subscriptionUserNotSubscribedToThisSubject() {
    return createResponseMessage(USER_NOT_SUB_OF_THIS_SUBJECT);
  }
}
