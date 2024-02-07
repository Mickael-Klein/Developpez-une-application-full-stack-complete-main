package com.openclassrooms.mdd.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PostDto {

  @NotNull
  private long id;

  @NotNull
  private String title;

  @NotNull
  private String content;

  @NotNull
  private long authorId;

  @NotNull
  private long subjectId;

  @NotNull
  private LocalDateTime createdAt;

  private List<CommentDto> commentDtos;
}
