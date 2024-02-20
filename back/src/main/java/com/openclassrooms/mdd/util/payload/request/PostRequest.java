package com.openclassrooms.mdd.util.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

  @NotNull
  private String title;

  @NotNull
  @Size(min = 10)
  private String content;

  @NotNull
  private long subjectId;
}
