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

@Component
public class EntityAndDtoCreation {

  @Autowired
  private DbUserFactory dbUserFactory;

  @Autowired
  private SubjectFactory subjectFactory;

  @Autowired
  private PostFactory postFactory;

  public DbUser getDbUserFromRegisterRequest(RegisterRequest registerRequest) {
    return dbUserFactory.getUserEntityFromRegisterRequest(registerRequest);
  }

  public DbUserDto getDbUserDtoFromDbUserEntity(DbUser dbUser) {
    return dbUserFactory.getUserDTOFromEntity(dbUser);
  }

  public DbUserDto getDbUserWithSubIdsFromDbUserWithSubEntity(DbUser dbUser) {
    return dbUserFactory.getUserWithSubDtoFromEntityWithSub(dbUser);
  }

  public SubjectDto getSubjectDtoFromSubjectEntity(Subject subject) {
    return subjectFactory.getSubjectDtoFromEntity(subject);
  }

  public List<SubjectDto> getSubjectDtoListFromSubjectEntityList(
    List<Subject> subjectList
  ) {
    return subjectList
      .stream()
      .map(this::getSubjectDtoFromSubjectEntity)
      .collect(Collectors.toList());
  }

  public SubjectDto getSubjectWithPostDtoFromSubjectWithPostEntity(
    Subject subject
  ) {
    return subjectFactory.getSujectWithPostListToDto(subject);
  }

  public PostDto getPostDtoFromPostEntity(Post post) {
    return postFactory.getPostDtoFromPostEntity(post);
  }

  public PostDto getPostWithCommentsDtoFromPostWithCommentEntity(Post post) {
    return postFactory.getPostWithCommentToDto(post);
  }
}
