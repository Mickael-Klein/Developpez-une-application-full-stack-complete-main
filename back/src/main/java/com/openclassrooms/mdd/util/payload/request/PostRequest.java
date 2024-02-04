package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {

  @NotNull
  private String title;

  @NotNull
  private String content;

  @NotNull
  private long subjectId;
}
