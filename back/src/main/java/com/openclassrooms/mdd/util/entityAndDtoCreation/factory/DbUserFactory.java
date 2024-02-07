package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.DbUserDto;
import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.util.payload.request.RegisterRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbUserFactory {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public DbUser getUserEntityFromRegisterRequest(
    RegisterRequest registerRequest
  ) {
    DbUser user = new DbUser(
      registerRequest.getEmail(),
      registerRequest.getUsername(),
      bCryptPasswordEncoder.encode(registerRequest.getPassword())
    );
    return user;
  }

  public DbUserDto getUserDTOFromEntity(DbUser user) {
    DbUserDto dbUserDto = new DbUserDto()
      .setId(user.getId())
      .setEmail(user.getEmail())
      .setUsername(user.getUsername())
      .setPassword(user.getPassword());
    return dbUserDto;
  }

  public DbUserDto getUserWithSubDtoFromEntityWithSub(DbUser dbUser) {
    List<Long> subjectIds = new ArrayList<>();
    for (Subject subject : dbUser.getSubjects()) {
      subjectIds.add(subject.getId());
    }
    Collections.sort(subjectIds);

    DbUserDto dbUserWithSubjectIdsDto = new DbUserDto(
      dbUser.getId(),
      dbUser.getEmail(),
      dbUser.getUsername(),
      dbUser.getPassword(),
      subjectIds
    );
    return dbUserWithSubjectIdsDto;
  }
}
