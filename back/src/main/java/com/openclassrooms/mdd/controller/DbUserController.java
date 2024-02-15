package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.DbUserDto;
import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.model.Subject;
import com.openclassrooms.mdd.service.JwtServiceImpl;
import com.openclassrooms.mdd.serviceInterface.DbUserInterface;
import com.openclassrooms.mdd.serviceInterface.SubjectInterface;
import com.openclassrooms.mdd.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mdd.util.payload.request.UpdateProfileRequest;
import com.openclassrooms.mdd.util.payload.response.DbUserAuthResponse;
import com.openclassrooms.mdd.util.payload.response.DbUserResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api/user")
public class DbUserController {

  @Autowired
  private DbUserInterface dbUserService;

  @Autowired
  private DbUserResponse dbUserResponse;

  @Autowired
  private DbUserAuthResponse dbUserAuthResponse;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private JwtServiceImpl jwtService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private SubjectInterface subjectService;

  @GetMapping("/me")
  public ResponseEntity<?> getMe(@AuthenticationPrincipal Jwt jwt) {
    try {
      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<DbUser> optionalDbUser = dbUserService.getUserById(userId);
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      DbUserDto dbUserDto = entityAndDtoCreation.getDbUserDtoFromDbUserEntity(
        optionalDbUser.get()
      );

      return ResponseEntity.ok().body(dbUserDto);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/mewithsub")
  public ResponseEntity<?> getMeWithSub(@AuthenticationPrincipal Jwt jwt) {
    try {
      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<DbUser> optionalDbUser = dbUserService.getUserByIdWithSub(
        userId
      );
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            optionalDbUser.get()
          )
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PutMapping("/updateme")
  public ResponseEntity<?> updateMe(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody UpdateProfileRequest updateProfileRequest,
    BindingResult bindingResult
  ) {
    try {
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.putUpdateProfilInvalidCredentials());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<DbUser> optionalDbUser = dbUserService.getUserById(userId);

      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      DbUser dbUser = optionalDbUser.get();

      DbUser updatedDbUser = new DbUser();
      updatedDbUser.setId(dbUser.getId());
      updatedDbUser.setComments(dbUser.getComments());
      updatedDbUser.setPosts(dbUser.getPosts());
      updatedDbUser.setSubjects(dbUser.getSubjects());

      if (updateProfileRequest.getEmail() != dbUser.getEmail()) {
        boolean isEmailAlreadyTaken = dbUserService.isEmailAlreadyTaken(
          updateProfileRequest.getEmail()
        );
        if (isEmailAlreadyTaken) {
          return ResponseEntity
            .badRequest()
            .body(
              dbUserAuthResponse.getRegisteringEmailAlreadyUsedResponseMessage()
            );
        }

        updatedDbUser.setEmail(updateProfileRequest.getEmail());
      } else {
        updatedDbUser.setEmail(dbUser.getEmail());
      }

      if (updateProfileRequest.getUsername() != dbUser.getUsername()) {
        boolean isUsernameAlreadyTaken = dbUserService.isUsernameAlreadyTaken(
          updateProfileRequest.getUsername()
        );
        if (isUsernameAlreadyTaken) {
          return ResponseEntity
            .badRequest()
            .body(
              dbUserAuthResponse.getRegisterUserameAlreadyUsedResponseMessage()
            );
        }

        updatedDbUser.setUsername(updateProfileRequest.getUsername());
      } else {
        updatedDbUser.setUsername(dbUser.getUsername());
      }

      if (updateProfileRequest.getPassword().length() > 0) {
        updatedDbUser.setPassword(
          bCryptPasswordEncoder.encode(updateProfileRequest.getPassword())
        );
      } else {
        updatedDbUser.setPassword(dbUser.getPassword());
      }

      DbUser savedUpdatedDbUser = dbUserService.saveUser(updatedDbUser);

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserDtoFromDbUserEntity(savedUpdatedDbUser)
        );
    } catch (Exception e) {
      System.err.println(e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/subscribe/{subjectId}")
  public ResponseEntity<?> subscribe(
    @AuthenticationPrincipal Jwt jwt,
    @PathVariable final long subjectId
  ) {
    try {
      List<Subject> subjectsList = subjectService.getAllSubject();
      boolean isSubjectIdPresentInSubjectList = isSubjectIdPresent(
        subjectsList,
        subjectId
      );
      if (!isSubjectIdPresentInSubjectList) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.subscriptionInvalidParameterSubjectId());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<DbUser> optionalDbUser = dbUserService.getUserByIdWithSub(
        userId
      );

      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      DbUser dbUser = optionalDbUser.get();

      boolean isUserAlreadySubOfThisSubject = isSubjectIdPresent(
        dbUser.getSubjects(),
        subjectId
      );
      if (isUserAlreadySubOfThisSubject) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.subscriptionUserAlreadySubscribed());
      }

      List<Subject> subjects = dbUser.getSubjects();
      subjects.add(subjectService.getSubjectById(subjectId).get());

      DbUser savedDbUser = dbUserService.saveUser(dbUser);

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            savedDbUser
          )
        );
    } catch (MethodArgumentTypeMismatchException | NumberFormatException e) {
      return ResponseEntity
        .badRequest()
        .body(dbUserResponse.subscriptionInvalidParameterSubjectIdType());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/unsubscribe/{subjectId}")
  public ResponseEntity<?> unsubscribe(
    @AuthenticationPrincipal Jwt jwt,
    @PathVariable final long subjectId
  ) {
    try {
      List<Subject> subjectsList = subjectService.getAllSubject();
      boolean isSubjectIdPresentInSubjectList = isSubjectIdPresent(
        subjectsList,
        subjectId
      );
      if (!isSubjectIdPresentInSubjectList) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.subscriptionInvalidParameterSubjectId());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<DbUser> optionalDbUser = dbUserService.getUserByIdWithSub(
        userId
      );

      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      DbUser dbUser = optionalDbUser.get();

      boolean isUserAlreadySubOfThisSubject = isSubjectIdPresent(
        dbUser.getSubjects(),
        subjectId
      );
      if (!isUserAlreadySubOfThisSubject) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.subscriptionUserNotSubscribedToThisSubject());
      }

      List<Subject> subjects = dbUser.getSubjects();
      subjects.removeIf(subject -> subject.getId() == subjectId);

      DbUser savedDbUser = dbUserService.saveUser(dbUser);

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            savedDbUser
          )
        );
    } catch (MethodArgumentTypeMismatchException | NumberFormatException e) {
      return ResponseEntity
        .badRequest()
        .body(dbUserResponse.subscriptionInvalidParameterSubjectIdType());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  private boolean isSubjectIdPresent(List<Subject> subjects, long targetId) {
    return subjects.stream().anyMatch(subject -> subject.getId() == targetId);
  }
}
