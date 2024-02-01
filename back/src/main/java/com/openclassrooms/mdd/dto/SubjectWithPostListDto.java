package com.openclassrooms.mdd.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectWithPostListDto {

  private long id;
  private String name;
  private List<PostDto> postDtos;
}
