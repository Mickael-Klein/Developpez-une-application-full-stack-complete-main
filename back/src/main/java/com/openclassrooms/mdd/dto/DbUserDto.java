package com.openclassrooms.mdd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DbUserDto {

  private long id;
  private String email;
  private String username;

  @JsonIgnore
  private String password;
}
