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

/**
 * Factory class for creating DbUser entities and DTOs.
 */
@Component
public class DbUserFactory {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Converts a RegisterRequest payload to a DbUser entity.
   *
   * @param registerRequest The RegisterRequest payload.
   * @return The corresponding DbUser entity.
   */
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

  /**
   * Converts a DbUser entity to a DbUserDto.
   *
   * @param user The DbUser entity.
   * @return The corresponding DbUserDto.
   */
  public DbUserDto getUserDTOFromEntity(DbUser user) {
    DbUserDto dbUserDto = new DbUserDto()
      .setId(user.getId())
      .setEmail(user.getEmail())
      .setUsername(user.getUsername())
      .setPassword(user.getPassword());
    return dbUserDto;
  }

  /**
   * Converts a DbUser entity with associated subjects to a DbUserDto with subject IDs.
   *
   * @param dbUser The DbUser entity with associated subjects.
   * @return The corresponding DbUserDto with subject IDs.
   */
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
