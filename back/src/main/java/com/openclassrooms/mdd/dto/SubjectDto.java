package com.openclassrooms.mdd.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) representing a subject.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SubjectDto {

  /** The unique identifier of the subject. */
  @NotNull
  private long id;

  /** The name of the subject. */
  @NotNull
  private String name;

  /** The description of the subject. */
  @NotNull
  private String description;

  /** The list of post DTOs associated with the subject. */
  private List<PostDto> postDtos;
}
