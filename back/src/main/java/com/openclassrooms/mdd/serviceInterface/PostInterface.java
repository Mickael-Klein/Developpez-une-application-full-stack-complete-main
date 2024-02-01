package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.Post;
import java.util.Optional;

public interface PostInterface {
  Post savePost(Post post);

  Optional<Post> getPostById(long id);
}
