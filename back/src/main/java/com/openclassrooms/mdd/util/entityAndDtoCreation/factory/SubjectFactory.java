package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.SubjectDto;
import com.openclassrooms.mdd.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectFactory {

  @Autowired
  private PostFactory postFactory;

  public SubjectDto getSubjectDtoFromEntity(Subject subject) {
    return new SubjectDto()
      .setId(subject.getId())
      .setName(subject.getName())
      .setDescription(subject.getDescription());
  }

  public SubjectDto getSujectWithPostListToDto(Subject subject) {
    return new SubjectDto(
      subject.getId(),
      subject.getName(),
      subject.getDescription(),
      postFactory.getPostDtoListFromPostEntityList(subject.getPosts())
    );
  }
}
