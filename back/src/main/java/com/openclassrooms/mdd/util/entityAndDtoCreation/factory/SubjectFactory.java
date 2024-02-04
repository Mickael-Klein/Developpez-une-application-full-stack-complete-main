package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.SubjectDto;
import com.openclassrooms.mdd.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectFactory {

  public SubjectDto getSubjectDtoFromEntity(Subject subject) {
    return new SubjectDto(subject.getId(), subject.getName());
  }
}
