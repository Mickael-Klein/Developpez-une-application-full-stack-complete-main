package com.openclassrooms.mdd.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {

  private long id;
  private String title;
  private String content;
  private long authorId;
  private long subjectId;
  private LocalDateTime createdAt;
}
