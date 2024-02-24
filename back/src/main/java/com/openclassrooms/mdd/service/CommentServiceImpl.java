package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.model.Comment;
import com.openclassrooms.mdd.repository.CommentRepository;
import com.openclassrooms.mdd.serviceInterface.CommentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CommentInterface service.
 */
@Service
public class CommentServiceImpl implements CommentInterface {

  @Autowired
  private CommentRepository commentRepository;

  /**
   * Saves a comment.
   *
   * @param comment The comment to save.
   * @return The saved comment.
   */
  @Override
  public Comment saveComment(Comment comment) {
    return commentRepository.save(comment);
  }
}
