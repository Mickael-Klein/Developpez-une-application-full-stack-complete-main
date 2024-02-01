package com.openclassrooms.mdd.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

  private long id;
  private String content;
  private long authorId;
  private LocalDateTime createdAt;
  private long postId;
}
