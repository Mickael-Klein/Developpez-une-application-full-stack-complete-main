package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data model for creation of comment request.
 */
@Data
public class CommentRequest {

  /**
   * The ID of the post to which the comment belongs.
   */
  @NotNull
  private long postId;

  /**
   * The content of the comment.
   */
  @NotNull
  private String content;
}
