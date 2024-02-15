package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.PostDto;
import com.openclassrooms.mdd.model.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostFactory {

  @Autowired
  private CommentFactory commentFactory;

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

  public List<PostDto> getPostDtoListFromPostEntityList(List<Post> postList) {
    return postList
      .stream()
      .map(post -> getPostDtoFromPostEntity(post))
      .collect(Collectors.toList());
  }

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
