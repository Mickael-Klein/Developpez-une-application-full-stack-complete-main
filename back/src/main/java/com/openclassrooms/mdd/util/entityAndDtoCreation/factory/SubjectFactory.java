package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.SubjectDto;
import com.openclassrooms.mdd.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating Subject DTOs.
 */
@Component
public class SubjectFactory {

  @Autowired
  private PostFactory postFactory;

  /**
   * Converts a Subject entity to a SubjectDto.
   *
   * @param subject The Subject entity to convert.
   * @return The corresponding SubjectDto.
   */
  public SubjectDto getSubjectDtoFromEntity(Subject subject) {
    return new SubjectDto()
      .setId(subject.getId())
      .setName(subject.getName())
      .setDescription(subject.getDescription());
  }

  /**
   * Converts a Subject entity with associated posts to a SubjectDto with post DTOs.
   *
   * @param subject The Subject entity with associated posts.
   * @return The corresponding SubjectDto with post DTOs.
   */
  public SubjectDto getSujectWithPostListToDto(Subject subject) {
    return new SubjectDto(
      subject.getId(),
      subject.getName(),
      subject.getDescription(),
      postFactory.getPostDtoListFromPostEntityList(subject.getPosts())
    );
  }
}
