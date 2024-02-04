package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data model for user registration request.
 */
@Data
public class RegisterRequest {

  /**
   * The email address for registration.
   */
  @NotBlank(message = "An email is required")
  @Email
  private String email;

  /**
   * The username for registration.
   */
  @NotBlank(message = "A username is required")
  private String username;

  /**
   * The password for registration.
   */
  @NotBlank(message = "A password is required")
  @Pattern(
    regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!£°;@#$%^&*()-+=]).*",
    message = "Password must respect criteria"
  )
  private String password;
}
