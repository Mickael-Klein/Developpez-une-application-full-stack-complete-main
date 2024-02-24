package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.repository.PostRepository;
import com.openclassrooms.mdd.serviceInterface.PostInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the PostInterface service.
 */
@Service
public class PostServiceImpl implements PostInterface {

  @Autowired
  private PostRepository postRepository;

  /**
   * Saves a post.
   *
   * @param post The post to save.
   * @return The saved post.
   */
  @Override
  public Post savePost(Post post) {
    return postRepository.save(post);
  }

  /**
   * Retrieves a post by ID.
   *
   * @param id The ID of the post to retrieve.
   * @return An Optional containing the post if found, empty otherwise.
   */
  @Override
  public Optional<Post> getPostById(long id) {
    return postRepository.findById(id);
  }

  /**
   * Retrieves a post by ID with associated comments eagerly fetched.
   *
   * @param id The ID of the post to retrieve.
   * @return An Optional containing the post if found, empty otherwise.
   */
  @Override
  public Optional<Post> getPostWithCommentsById(long id) {
    return postRepository
      .findById(id)
      .map(post -> {
        post.getComments().size();
        return post;
      });
  }
}
