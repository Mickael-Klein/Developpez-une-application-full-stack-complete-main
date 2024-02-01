package com.openclassrooms.mdd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DbUserWithSubjectListDto {

  private long id;
  private String email;
  private String username;

  @JsonIgnore
  private String password;

  private List<SubjectDto> subjectDtos;
}
