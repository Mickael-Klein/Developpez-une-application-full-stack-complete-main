package com.openclassrooms.mdd.util.entityAndDtoCreation;

import com.openclassrooms.mdd.dto.DbUserDto;
import com.openclassrooms.mdd.dto.DbUserWithSubjectListDto;
import com.openclassrooms.mdd.dto.SubjectDto;
import com.openclassrooms.mdd.dto.SubjectWithPostListDto;
import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.util.entityAndDtoCreation.factory.DbUserFactory;
import com.openclassrooms.mdd.util.entityAndDtoCreation.factory.SubjectFactory;
import com.openclassrooms.mdd.util.payload.request.RegisterRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

  public DbUser getDbUserFromRegisterRequest(RegisterRequest registerRequest) {
    return dbUserFactory.getUserEntityFromRegisterRequest(registerRequest);
  }

  public DbUserDto getDbUserDtoFromDbUserEntity(DbUser dbUser) {
    return dbUserFactory.getUserDTOFromEntity(dbUser);
  }

  public DbUserWithSubjectListDto getDbUserWithSubDtoFromDbUserWithSubEntity(
    DbUser dbUser
  ) {
    List<SubjectDto> subjectDtos = new ArrayList<>();
    for (Subject subject : dbUser.getSubjects()) {
      subjectDtos.add(subjectFactory.getSubjectDtoFromEntity(subject));
    }

    Collections.sort(subjectDtos, Comparator.comparing(SubjectDto::getId));

    return dbUserFactory.getUserWithSubDtoFromEntityWithSub(
      dbUser,
      subjectDtos
    );
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

  public SubjectWithPostListDto getSubjectWithPostDtoFromSubjectWithPostEntity(
    Subject subject
  ) {
    return subjectFactory.getSujectWithPostListToDto(subject);
  }
}
