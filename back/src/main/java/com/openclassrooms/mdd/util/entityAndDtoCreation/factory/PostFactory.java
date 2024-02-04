package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.PostDto;
import com.openclassrooms.mdd.model.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostFactory {

  public PostDto getPostDtoFromPostEntity(Post post) {
    return new PostDto(
      post.getId(),
      post.getTitle(),
      post.getContent(),
      post.getDbUser().getId(),
      post.getSubject().getId(),
      post.getCreatedAt()
    );
  }

  public List<PostDto> getPostDtoListFromPostEntityList(List<Post> postList) {
    return postList
      .stream()
      .map(post -> getPostDtoFromPostEntity(post))
      .collect(Collectors.toList());
  }
}
