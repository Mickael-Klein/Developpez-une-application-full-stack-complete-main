package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.Comment;

/**
 * Interface for managing Comment entities.
 */
public interface CommentInterface {
  /**
   * Saves a comment.
   *
   * @param comment The comment to save.
   * @return The saved comment.
   */
  Comment saveComment(Comment comment);
}
