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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Controller for handling user-related operations.
 */
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

  /**
   * Retrieves the currently authenticated user.
   *
   * @param jwt The JWT token representing the current user.
   * @return ResponseEntity containing the user's details if found, or an error response if not found or an error occurs.
   */
  @GetMapping("/me")
  public ResponseEntity<?> getMe(@AuthenticationPrincipal Jwt jwt) {
    try {
      // Extract user ID from JWT token
      long userId = jwtService.getUserIdFromJwtLong(jwt);

      // Retrieve user from the database
      Optional<DbUser> optionalDbUser = dbUserService.getUserById(userId);

      // Check if user exists
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      // Convert DbUser entity to DTO
      DbUserDto dbUserDto = entityAndDtoCreation.getDbUserDtoFromDbUserEntity(
        optionalDbUser.get()
      );

      // Return user DTO in the response
      return ResponseEntity.ok().body(dbUserDto);
    } catch (Exception e) {
      // Return internal server error if an exception occurs
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Retrieves the currently authenticated user along with their subscribed subjects.
   *
   * @param jwt The JWT token representing the current user.
   * @return ResponseEntity containing the user's details with subscribed subjects if found, or an error response if not found or an error occurs.
   */
  @GetMapping("/mewithsub")
  public ResponseEntity<?> getMeWithSub(@AuthenticationPrincipal Jwt jwt) {
    try {
      // Extract user ID from JWT token
      long userId = jwtService.getUserIdFromJwtLong(jwt);

      // Retrieve user with subscribed subjects from the database
      Optional<DbUser> optionalDbUser = dbUserService.getUserByIdWithSub(
        userId
      );

      // Check if user exists
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      // Convert DbUser entity with subscribed subjects to DTO
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            optionalDbUser.get()
          )
        );
    } catch (Exception e) {
      // Return internal server error if an exception occurs
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Updates the profile of the currently authenticated user.
   *
   * @param jwt The JWT token representing the current user.
   * @param updateProfileRequest The request body containing the updated profile information.
   * @param bindingResult The result of the validation on the request body.
   * @return ResponseEntity containing the updated user's details with subscribed subjects if the update is successful, or an error response if not found, validation fails, or an error occurs.
   */
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

      // Get the user entity
      DbUser dbUser = optionalDbUser.get();

      // Create a new DbUser instance for updating
      DbUser updatedDbUser = new DbUser();
      updatedDbUser.setId(dbUser.getId());
      updatedDbUser.setComments(dbUser.getComments());
      updatedDbUser.setPosts(dbUser.getPosts());
      updatedDbUser.setSubjects(dbUser.getSubjects());

      // Update email if it's different from the current one
      if (!updateProfileRequest.getEmail().equals(dbUser.getEmail())) {
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

      // Update username if it's different from the current one
      if (!updateProfileRequest.getUsername().equals(dbUser.getUsername())) {
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

      // Update password if provided
      if (updateProfileRequest.getPassword() != null) {
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
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            savedUpdatedDbUser
          )
        );
    } catch (Exception e) {
      System.err.println(e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Subscribes the current user to a specified subject.
   *
   * @param jwt The JWT token representing the current user.
   * @param subjectId The ID of the subject to subscribe to.
   * @return ResponseEntity containing the updated user's details with subscribed subjects if the subscription is successful, or an error response if the subject ID is invalid, the user is not found, the user is already subscribed to the subject, or an error occurs.
   */
  @PostMapping("/subscribe/{subjectId}")
  public ResponseEntity<?> subscribe(
    @AuthenticationPrincipal Jwt jwt,
    @PathVariable final long subjectId
  ) {
    try {
      // Get all subjects from the database
      List<Subject> subjectsList = subjectService.getAllSubject();
      // Check if the subject ID is present in the list of subjects
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

      // Check if user is already subscribed to the subject
      boolean isUserAlreadySubOfThisSubject = isSubjectIdPresent(
        dbUser.getSubjects(),
        subjectId
      );
      if (isUserAlreadySubOfThisSubject) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.subscriptionUserAlreadySubscribed());
      }

      // Add the subject to the user's list of subscribed subjects
      List<Subject> subjects = dbUser.getSubjects();
      subjects.add(subjectService.getSubjectById(subjectId).get());

      DbUser savedDbUser = dbUserService.saveUser(dbUser);

      // Return the updated user DTO in the response
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

  /**
   * Unsubscribes the current user from a specified subject.
   *
   * @param jwt The JWT token representing the current user.
   * @param subjectId The ID of the subject to unsubscribe from.
   * @return ResponseEntity containing the updated user's details with subscribed subjects if the unsubscription is successful, or an error response if the subject ID is invalid, the user is not found, the user is not subscribed to the subject, or an error occurs.
   */
  @DeleteMapping("/unsubscribe/{subjectId}")
  public ResponseEntity<?> unsubscribe(
    @AuthenticationPrincipal Jwt jwt,
    @PathVariable final long subjectId
  ) {
    try {
      // Get all subjects from the database
      List<Subject> subjectsList = subjectService.getAllSubject();
      // Check if the subject ID is present in the list of subjects
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

      // Check if user is already subscribed to the subject
      boolean isUserAlreadySubOfThisSubject = isSubjectIdPresent(
        dbUser.getSubjects(),
        subjectId
      );
      if (!isUserAlreadySubOfThisSubject) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.subscriptionUserNotSubscribedToThisSubject());
      }

      // Remove the subject from the user's list of subscribed subjects
      List<Subject> subjects = dbUser.getSubjects();
      subjects.removeIf(subject -> subject.getId() == subjectId);

      DbUser savedDbUser = dbUserService.saveUser(dbUser);

      // Return the updated user DTO in the response
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
