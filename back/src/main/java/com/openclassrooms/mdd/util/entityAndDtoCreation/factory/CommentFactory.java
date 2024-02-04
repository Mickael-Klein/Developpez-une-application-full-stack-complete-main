package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.CommentDto;
import com.openclassrooms.mdd.model.Comment;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CommentFactory {

  public CommentDto commentToDto(Comment comment) {
    return new CommentDto(
      comment.getId(),
      comment.getContent(),
      comment.getDbUser().getId(),
      comment.getCreatedAt(),
      comment.getPost().getId()
    );
  }

  public List<CommentDto> commentListToDto(List<Comment> commentList) {
    return commentList
      .stream()
      .map(comment -> commentToDto(comment))
      .collect(Collectors.toList());
  }
}
