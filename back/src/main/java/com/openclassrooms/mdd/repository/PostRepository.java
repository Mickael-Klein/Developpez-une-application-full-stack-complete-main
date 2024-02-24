package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Post entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
public interface PostRepository extends JpaRepository<Post, Long> {}
