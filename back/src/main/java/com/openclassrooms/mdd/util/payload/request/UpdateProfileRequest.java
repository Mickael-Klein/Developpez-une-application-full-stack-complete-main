package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateProfileRequest {

  @NotBlank(message = "An email is required")
  @Email
  private String email;

  @NotBlank(message = "A username is required")
  private String username;

  @Pattern(
    regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!£°;@#$%^&*()-+=]).*",
    message = "Password must respect criteria"
  )
  private String password;
}
