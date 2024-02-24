package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.CommentDto;
import com.openclassrooms.mdd.model.Comment;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating Comment DTOs.
 */
@Component
public class CommentFactory {

  /**
   * Converts a Comment entity to a CommentDto.
   *
   * @param comment The Comment entity to convert.
   * @return The corresponding CommentDto.
   */
  public CommentDto commentToDto(Comment comment) {
    return new CommentDto(
      comment.getId(),
      comment.getContent(),
      comment.getDbUser().getId(),
      comment.getDbUser().getUsername(),
      comment.getCreatedAt(),
      comment.getPost().getId()
    );
  }

  /**
   * Converts a list of Comment entities to a list of CommentDto objects.
   *
   * @param commentList The list of Comment entities to convert.
   * @return The list of corresponding CommentDto objects.
   */
  public List<CommentDto> commentListToDto(List<Comment> commentList) {
    return commentList
      .stream()
      .map(comment -> commentToDto(comment))
      .collect(Collectors.toList());
  }
}
