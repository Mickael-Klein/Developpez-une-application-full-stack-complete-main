package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

  @NotNull
  private String content;
}
