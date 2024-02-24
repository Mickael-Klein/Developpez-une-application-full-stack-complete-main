package com.openclassrooms.mdd.util.entityAndDtoCreation;

import com.openclassrooms.mdd.dto.DbUserDto;
import com.openclassrooms.mdd.dto.PostDto;
import com.openclassrooms.mdd.dto.SubjectDto;
import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.model.Post;
import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.util.entityAndDtoCreation.factory.DbUserFactory;
import com.openclassrooms.mdd.util.entityAndDtoCreation.factory.PostFactory;
import com.openclassrooms.mdd.util.entityAndDtoCreation.factory.SubjectFactory;
import com.openclassrooms.mdd.util.payload.request.RegisterRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Utility class for creating entities and DTOs.
 */
@Component
public class EntityAndDtoCreation {

  @Autowired
  private DbUserFactory dbUserFactory;

  @Autowired
  private SubjectFactory subjectFactory;

  @Autowired
  private PostFactory postFactory;

  /**
   * Converts a RegisterRequest object to a DbUser entity.
   *
   * @param registerRequest The RegisterRequest object to convert.
   * @return The corresponding DbUser entity.
   */
  public DbUser getDbUserFromRegisterRequest(RegisterRequest registerRequest) {
    return dbUserFactory.getUserEntityFromRegisterRequest(registerRequest);
  }

  /**
   * Converts a DbUser entity to a DbUserDto.
   *
   * @param dbUser The DbUser entity to convert.
   * @return The corresponding DbUserDto.
   */
  public DbUserDto getDbUserDtoFromDbUserEntity(DbUser dbUser) {
    return dbUserFactory.getUserDTOFromEntity(dbUser);
  }

  /**
   * Converts a DbUser entity with associated subjects to a DbUserDto with subject IDs.
   *
   * @param dbUser The DbUser entity with associated subjects.
   * @return The corresponding DbUserDto with subject IDs.
   */
  public DbUserDto getDbUserWithSubIdsFromDbUserWithSubEntity(DbUser dbUser) {
    return dbUserFactory.getUserWithSubDtoFromEntityWithSub(dbUser);
  }

  /**
   * Converts a Subject entity to a SubjectDto.
   *
   * @param subject The Subject entity to convert.
   * @return The corresponding SubjectDto.
   */
  public SubjectDto getSubjectDtoFromSubjectEntity(Subject subject) {
    return subjectFactory.getSubjectDtoFromEntity(subject);
  }

  /**
   * Converts a list of Subject entities to a list of SubjectDto objects.
   *
   * @param subjectList The list of Subject entities to convert.
   * @return The list of corresponding SubjectDto objects.
   */
  public List<SubjectDto> getSubjectDtoListFromSubjectEntityList(
    List<Subject> subjectList
  ) {
    return subjectList
      .stream()
      .map(this::getSubjectDtoFromSubjectEntity)
      .collect(Collectors.toList());
  }

  /**
   * Converts a Subject entity with associated posts to a SubjectDto with post DTOs.
   *
   * @param subject The Subject entity with associated posts.
   * @return The corresponding SubjectDto with post DTOs.
   */
  public SubjectDto getSubjectWithPostDtoFromSubjectWithPostEntity(
    Subject subject
  ) {
    return subjectFactory.getSujectWithPostListToDto(subject);
  }

  /**
   * Converts a Post entity to a PostDto.
   *
   * @param post The Post entity to convert.
   * @return The corresponding PostDto.
   */
  public PostDto getPostDtoFromPostEntity(Post post) {
    return postFactory.getPostDtoFromPostEntity(post);
  }

  /**
   * Converts a Post entity with associated comments to a PostDto with comment DTOs.
   *
   * @param post The Post entity with associated comments.
   * @return The corresponding PostDto with comment DTOs.
   */
  public PostDto getPostWithCommentsDtoFromPostWithCommentEntity(Post post) {
    return postFactory.getPostWithCommentToDto(post);
  }
}
