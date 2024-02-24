package com.openclassrooms.mdd.util.payload.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Response payload for subject-related operations.
 */
@Component
public class SubjectResponse {

  // Message constants
  private static final String MESSAGE_TITLE = "message";
  private static final String INVALID_SUBJECT_ID_PARAMETER =
    "the id parameter provided doesn't match any existing subject";

  /**
   * Creates a response message with the provided message.
   * @param message The message to include in the response.
   * @return A map containing the response message.
   */
  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(MESSAGE_TITLE, message);
    return responseBody;
  }

  /**
   * Creates a response message for an invalid subject ID parameter.
   * @return A map containing the response message.
   */
  public Map<String, String> getSubjectInvalidIdParameter() {
    return createResponseMessage(INVALID_SUBJECT_ID_PARAMETER);
  }
}
