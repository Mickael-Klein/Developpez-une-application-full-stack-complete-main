package com.openclassrooms.mdd.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) representing a post.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PostDto {

  /** The unique identifier of the post. */
  @NotNull
  private long id;

  /** The title of the post. */
  @NotNull
  private String title;

  /** The content of the post. */
  @NotNull
  private String content;

  /** The unique identifier of the author of the post. */
  @NotNull
  private long authorId;

  /** The name of the author of the post. */
  @NotNull
  private String authorName;

  /** The unique identifier of the subject of the post. */
  @NotNull
  private long subjectId;

  /** The name of the subject of the post. */
  @NotNull
  private String subjectName;

  /** The date and time when the post was created. */
  @NotNull
  private LocalDateTime createdAt;

  /** The list of comment DTOs associated with the post. */
  private List<CommentDto> commentDtos;
}
