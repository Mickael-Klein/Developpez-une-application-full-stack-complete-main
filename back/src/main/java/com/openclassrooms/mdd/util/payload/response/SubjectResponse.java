package com.openclassrooms.mdd.util.payload.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SubjectResponse {

  private static final String MESSAGE_TITLE = "message";
  private static final String INVALID_SUBJECT_ID_PARAMETER =
    "the id parameter provided doesn't match any existing subject";

  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(MESSAGE_TITLE, message);
    return responseBody;
  }

  public Map<String, String> getSubjectInvalidIdParameter() {
    return createResponseMessage(INVALID_SUBJECT_ID_PARAMETER);
  }
}
