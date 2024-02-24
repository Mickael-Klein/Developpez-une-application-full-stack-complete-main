package com.openclassrooms.mdd.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a comment.
 */
@Data
@AllArgsConstructor
public class CommentDto {

  /** The unique identifier of the comment. */
  private long id;

  /** The content of the comment. */
  private String content;

  /** The unique identifier of the author of the comment. */
  private long authorId;

  /** The name of the author of the comment. */
  private String authorName;

  /** The date and time when the comment was created. */
  private LocalDateTime createdAt;

  /** The unique identifier of the post to which the comment belongs. */
  private long postId;
}
