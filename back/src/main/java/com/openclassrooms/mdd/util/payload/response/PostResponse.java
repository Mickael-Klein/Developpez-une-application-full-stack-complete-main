package com.openclassrooms.mdd.util.payload.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PostResponse {

  private static final String MESSAGE_TITLE = "message";
  private static final String INVALID_POST_REQUEST = "invalid post request";
  private static final String INVALID_SUBJECT_ID =
    "no subject found with this subject id";
  private static final String INVALID_POST_ID = "no post found with this id";

  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(MESSAGE_TITLE, message);
    return responseBody;
  }

  public Map<String, String> postPostInvalidPayload() {
    return createResponseMessage(INVALID_POST_REQUEST);
  }

  public Map<String, String> postPostInvalidSubjectId() {
    return createResponseMessage(INVALID_SUBJECT_ID);
  }

  public Map<String, String> getPostInvalidPostId() {
    return createResponseMessage(INVALID_POST_ID);
  }
}
