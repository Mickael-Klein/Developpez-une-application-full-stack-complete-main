package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.repository.PostRepository;
import com.openclassrooms.mdd.serviceInterface.PostInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostInterface {

  @Autowired
  private PostRepository postRepository;

  @Override
  public Post savePost(Post post) {
    return postRepository.save(post);
  }

  @Override
  public Optional<Post> getPostById(long id) {
    return postRepository.findById(id);
  }
}
