package com.openclassrooms.mdd.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SubjectDto {

  @NotNull
  private long id;

  @NotNull
  private String name;

  @NotNull
  private String description;

  private List<PostDto> postDtos;
}
