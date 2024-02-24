package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.repository.SubjectRepository;
import com.openclassrooms.mdd.serviceInterface.SubjectInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the SubjectInterface service.
 */
@Service
public class SubjectServiceImpl implements SubjectInterface {

  @Autowired
  private SubjectRepository subjectRepository;

  /**
   * Retrieves all subjects.
   *
   * @return The list of all subjects.
   */
  @Override
  public List<Subject> getAllSubject() {
    return subjectRepository.findAll();
  }

  /**
   * Retrieves a subject by ID.
   *
   * @param id The ID of the subject to retrieve.
   * @return An Optional containing the subject if found, empty otherwise.
   */
  @Override
  public Optional<Subject> getSubjectById(long id) {
    return subjectRepository.findById(id);
  }

  /**
   * Retrieves a subject by ID with associated posts eagerly fetched.
   *
   * @param id The ID of the subject to retrieve.
   * @return An Optional containing the subject if found, empty otherwise.
   */
  @Override
  public Optional<Subject> getSubjectByIdWithPost(long id) {
    return subjectRepository
      .findById(id)
      .map(subject -> {
        subject.getPosts().size();
        return subject;
      });
  }
}
