package com.openclassrooms.mdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectDto {

  private long id;
  private String name;
  private String description;
}
