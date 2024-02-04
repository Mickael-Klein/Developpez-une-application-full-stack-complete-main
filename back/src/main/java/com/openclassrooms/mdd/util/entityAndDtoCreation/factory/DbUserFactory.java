package com.openclassrooms.mdd.util.entityAndDtoCreation.factory;

import com.openclassrooms.mdd.dto.DbUserDto;
import com.openclassrooms.mdd.dto.DbUserWithSubjectListDto;
import com.openclassrooms.mdd.dto.SubjectDto;
import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.util.payload.request.RegisterRequest;
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
    DbUserDto dbUserDto = new DbUserDto(
      user.getId(),
      user.getEmail(),
      user.getUsername(),
      user.getPassword()
    );
    return dbUserDto;
  }

  public DbUserWithSubjectListDto getUserWithSubDtoFromEntityWithSub(
    DbUser dbUser,
    List<SubjectDto> subjectDtos
  ) {
    DbUserWithSubjectListDto dbUserWithSubjectListDto = new DbUserWithSubjectListDto(
      dbUser.getId(),
      dbUser.getEmail(),
      dbUser.getUsername(),
      dbUser.getPassword(),
      subjectDtos
    );
    return dbUserWithSubjectListDto;
  }
}
