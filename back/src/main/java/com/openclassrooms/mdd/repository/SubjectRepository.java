package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Subject entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {}
