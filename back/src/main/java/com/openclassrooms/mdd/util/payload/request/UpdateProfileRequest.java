package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data model for updating a user's profile request.
 */
@Data
public class UpdateProfileRequest {

  /**
   * The updated email of the user.
   * Must not be blank and must be a valid email address.
   */
  @NotBlank(message = "An email is required")
  @Email
  private String email;

  /**
   * The updated username of the user.
   * Must not be blank.
   */
  @NotBlank(message = "A username is required")
  private String username;

  /**
   * The updated password of the user.
   * Must meet certain criteria defined by the specified regular expression.
   */
  @Pattern(
    regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!£°;@#$%^&*()-+=]).*",
    message = "Password must respect criteria"
  )
  private String password;
}
