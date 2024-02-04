package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectInterface {
  List<Subject> getAllSubject();

  Optional<Subject> getSubjectById(long id);

  Optional<Subject> getSubjectByIdWithPost(long id);
}
