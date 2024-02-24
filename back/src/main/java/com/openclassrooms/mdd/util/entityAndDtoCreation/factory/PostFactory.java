package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.PostDto;
import com.openclassrooms.mdd.model.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating Post DTOs.
 */
@Component
public class PostFactory {

  @Autowired
  private CommentFactory commentFactory;

  /**
   * Converts a Post entity to a PostDto.
   *
   * @param post The Post entity to convert.
   * @return The corresponding PostDto.
   */
  public PostDto getPostDtoFromPostEntity(Post post) {
    return new PostDto()
      .setId(post.getId())
      .setTitle(post.getTitle())
      .setContent(post.getContent())
      .setAuthorId(post.getDbUser().getId())
      .setAuthorName(post.getDbUser().getUsername())
      .setSubjectId(post.getSubject().getId())
      .setSubjectName(post.getSubject().getName())
      .setCreatedAt(post.getCreatedAt());
  }

  /**
   * Converts a list of Post entities to a list of PostDto objects.
   *
   * @param postList The list of Post entities to convert.
   * @return The list of corresponding PostDto objects.
   */
  public List<PostDto> getPostDtoListFromPostEntityList(List<Post> postList) {
    return postList
      .stream()
      .map(post -> getPostDtoFromPostEntity(post))
      .collect(Collectors.toList());
  }

  /**
   * Converts a Post entity with associated comments to a PostDto with comment DTOs.
   *
   * @param post The Post entity with associated comments.
   * @return The corresponding PostDto with comment DTOs.
   */
  public PostDto getPostWithCommentToDto(Post post) {
    return new PostDto()
      .setId(post.getId())
      .setTitle(post.getTitle())
      .setContent(post.getContent())
      .setAuthorId(post.getDbUser().getId())
      .setAuthorName(post.getDbUser().getUsername())
      .setSubjectId(post.getSubject().getId())
      .setSubjectName(post.getSubject().getName())
      .setCreatedAt(post.getCreatedAt())
      .setCommentDtos(commentFactory.commentListToDto(post.getComments()));
  }
}
