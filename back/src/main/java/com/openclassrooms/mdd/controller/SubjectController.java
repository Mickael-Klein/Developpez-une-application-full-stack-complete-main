package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.serviceInterface.SubjectInterface;
import com.openclassrooms.mdd.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mdd.util.payload.response.SubjectResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

 * Controller for handling subject-related operations.
 */

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

  @Autowired
  private SubjectInterface subjectService;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private SubjectResponse subjectResponse;

  /**
   * Retrieves all subjects.
   *
   * @return ResponseEntity containing the list of subjects if successful, or an error response if an exception occurs.
   */
  @GetMapping("/getall")
  public ResponseEntity<?> getAllSubjects() {
    try {
      List<Subject> subjects = subjectService.getAllSubject();
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getSubjectDtoListFromSubjectEntityList(subjects)
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Retrieves a subject by its ID.
   *
   * @param subjectId The ID of the subject to retrieve.
   * @return ResponseEntity containing the subject if found, or an error response if the ID is invalid or an exception occurs.
   */
  @GetMapping("/getbyid/{subjectId}")
  public ResponseEntity<?> getSubjectById(@PathVariable final long subjectId) {
    try {
      Optional<Subject> optionalSubject = subjectService.getSubjectById(
        subjectId
      );
      if (!optionalSubject.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(subjectResponse.getSubjectInvalidIdParameter());
      }

      Subject subject = optionalSubject.get();

      return ResponseEntity
        .ok()
        .body(entityAndDtoCreation.getSubjectDtoFromSubjectEntity(subject));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Retrieves a subject by its ID with associated posts.
   *
   * @param subjectId The ID of the subject to retrieve.
   * @return ResponseEntity containing the subject with associated posts if found, or an error response if the ID is invalid or an exception occurs.
   */
  @GetMapping("/getbyidwithpost/{subjectId}")
  public ResponseEntity<?> getSubjectByIdWithPost(
    @PathVariable final long subjectId
  ) {
    try {
      Optional<Subject> optionalSubject = subjectService.getSubjectByIdWithPost(
        subjectId
      );
      if (!optionalSubject.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(subjectResponse.getSubjectInvalidIdParameter());
      }

      Subject subject = optionalSubject.get();

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getSubjectWithPostDtoFromSubjectWithPostEntity(
            subject
          )
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
