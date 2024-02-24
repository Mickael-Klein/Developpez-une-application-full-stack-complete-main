package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.Subject;
import java.util.List;
import java.util.Optional;

/**
 * Interface for managing Subject entities.
 */
public interface SubjectInterface {
  /**
   * Retrieves all subjects.
   *
   * @return The list of all subjects.
   */
  List<Subject> getAllSubject();

  /**
   * Retrieves a subject by ID.
   *
   * @param id The ID of the subject to retrieve.
   * @return An Optional containing the subject if found, empty otherwise.
   */
  Optional<Subject> getSubjectById(long id);

  /**
   * Retrieves a subject by ID with associated posts eagerly fetched.
   *
   * @param id The ID of the subject to retrieve.
   * @return An Optional containing the subject if found, empty otherwise.
   */
  Optional<Subject> getSubjectByIdWithPost(long id);
}
