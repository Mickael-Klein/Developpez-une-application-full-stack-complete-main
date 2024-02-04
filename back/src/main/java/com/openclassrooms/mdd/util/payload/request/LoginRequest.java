package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data model for a login request.
 */
@Data
public class LoginRequest {

  /**
   * The email used for login.
   */
  @NotBlank(message = "An email or username is required for login")
  private String emailOrUsername;

  /**
   * The password used for login.
   */
  @NotBlank(message = "A password is required for login")
  private String password;
}
