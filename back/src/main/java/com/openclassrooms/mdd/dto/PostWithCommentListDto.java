package com.openclassrooms.mdd.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostWithCommentListDto {

  private long id;
  private String title;
  private String content;
  private long authorId;
  private long subjectId;
  private LocalDateTime createdAt;
  private List<CommentDto> commentDtos;
}
