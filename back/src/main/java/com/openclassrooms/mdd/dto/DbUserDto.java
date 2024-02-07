package com.openclassrooms.mdd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class DbUserDto {

  @NotNull
  private long id;

  @NotNull
  private String email;

  @NotNull
  private String username;

  @JsonIgnore
  private String password;

  private List<Long> subjectIds;
}
