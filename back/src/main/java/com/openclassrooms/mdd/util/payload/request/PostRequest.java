package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data model for post creation request.
 */
@Data
public class PostRequest {

  /**
   * The title of the post.
   */
  @NotNull
  private String title;

  /**
   * The content of the post.
   * It must have a minimum length of 10 characters.
   */
  @NotNull
  @Size(min = 10)
  private String content;

  /**
   * The ID of the subject to which the post belongs.
   */
  @NotNull
  private long subjectId;
}
