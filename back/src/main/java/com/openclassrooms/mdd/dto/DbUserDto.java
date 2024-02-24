package com.openclassrooms.mdd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) representing a database user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class DbUserDto {

  /** The unique identifier of the user. */
  @NotNull
  private long id;

  /** The email address of the user. */
  @NotNull
  private String email;

  /** The username of the user. */
  @NotNull
  private String username;

  /** The password of the user. This field is ignored during JSON serialization. */
  @JsonIgnore
  private String password;

  /** The list of subject IDs associated with the user. */
  private List<Long> subjectIds;
}
