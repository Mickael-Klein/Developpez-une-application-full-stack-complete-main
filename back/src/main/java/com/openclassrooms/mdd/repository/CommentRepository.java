package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Comment entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {}
