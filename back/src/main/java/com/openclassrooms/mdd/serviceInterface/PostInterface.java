package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.Post;
import java.util.Optional;

/**
 * Interface for managing Post entities.
 */
public interface PostInterface {
  /**
   * Saves a post.
   *
   * @param post The post to save.
   * @return The saved post.
   */
  Post savePost(Post post);

  /**
   * Retrieves a post by ID.
   *
   * @param id The ID of the post to retrieve.
   * @return An Optional containing the post if found, empty otherwise.
   */
  Optional<Post> getPostById(long id);

  /**
   * Retrieves a post by ID with associated comments eagerly fetched.
   *
   * @param id The ID of the post to retrieve.
   * @return An Optional containing the post if found, empty otherwise.
   */
  Optional<Post> getPostWithCommentsById(long id);
}
