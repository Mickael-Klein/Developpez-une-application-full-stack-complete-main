package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.serviceInterface.DbUserInterface;
import com.openclassrooms.mdd.serviceInterface.JwtInterface;
import com.openclassrooms.mdd.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mdd.util.payload.request.LoginRequest;
import com.openclassrooms.mdd.util.payload.request.RegisterRequest;
import com.openclassrooms.mdd.util.payload.response.DbUserAuthResponse;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class DbUserAuthController {

  @Autowired
  private DbUserInterface dbUserService;

  @Autowired
  private JwtInterface jwtService;

  @Autowired
  private DbUserAuthResponse dbUserAuthResponse;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  /**
   * Endpoint for registering a new user account.
   * @param registerRequest The request payload containing registration data.
   * @param bindingResult The result of request payload validation.
   * @return ResponseEntity containing the response to the registration request.
   */
  @PostMapping("/register")
  public ResponseEntity<?> registerAccount(
    @Valid @RequestBody RegisterRequest registerRequest,
    BindingResult bindingResult
  ) {
    try {
      // Check if the request payload is invalid
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(
            dbUserAuthResponse.getRegisteringBadCredentialsResponseMessage()
          );
      }

      // Check if the email is already registered
      boolean isEmailAlreadyRegistered = dbUserService.isEmailAlreadyTaken(
        registerRequest.getEmail()
      );
      if (isEmailAlreadyRegistered) {
        return ResponseEntity
          .badRequest()
          .body(
            dbUserAuthResponse.getRegisteringEmailAlreadyUsedResponseMessage()
          );
      }

      // Check if the username is already taken
      boolean isUsernameAlreadyTaken = dbUserService.isUsernameAlreadyTaken(
        registerRequest.getUsername()
      );
      if (isUsernameAlreadyTaken) {
        return ResponseEntity
          .badRequest()
          .body(
            dbUserAuthResponse.getRegisterUserameAlreadyUsedResponseMessage()
          );
      }

      // Create and save the user
      DbUser newUser = entityAndDtoCreation.getDbUserFromRegisterRequest(
        registerRequest
      );
      dbUserService.saveUser(newUser);

      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Endpoint for user login.
   * @param loginRequest The request payload containing login credentials.
   * @param bindingResult The result of request payload validation.
   * @return ResponseEntity containing the response to the login request.
   */
  @PostMapping("/login")
  public ResponseEntity<?> loginAccount(
    @Valid @RequestBody LoginRequest loginRequest,
    BindingResult bindingResult
  ) {
    try {
      // Check if the request payload is invalid
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(dbUserAuthResponse.getLoginBadCredentialsResponseMessage());
      }

      // Check if the user exists
      DbUser dbUser = doesUserExist(loginRequest.getEmailOrUsername());

      if (dbUser == null) {
        return ResponseEntity
          .badRequest()
          .body(dbUserAuthResponse.getLoginBadCredentialsResponseMessage());
      }

      // Check if the password is correct
      boolean isPasswordCorrect = dbUserService.isPasswordValid(
        loginRequest.getPassword(),
        dbUser
      );

      if (!isPasswordCorrect) {
        return ResponseEntity
          .badRequest()
          .body(dbUserAuthResponse.getLoginBadCredentialsResponseMessage());
      }

      // Generate JWT token
      String jwtToken = jwtService.generateToken(dbUser.getId());

      return ResponseEntity
        .ok()
        .body(dbUserAuthResponse.getJwtTokenResponseMessage(jwtToken));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Helper method to check if a user exists based on email or username.
   * @param emailOrUsername The email or username provided in the login request.
   * @return The user if found, otherwise null.
   */
  private DbUser doesUserExist(String emailOrUsername) {
    Optional<DbUser> optionalDbUserByEmail = dbUserService.getUserByEmail(
      emailOrUsername
    );
    Optional<DbUser> optionalDbUserByUsername = dbUserService.getUserByUsername(
      emailOrUsername
    );

    if (optionalDbUserByEmail.isPresent()) {
      return optionalDbUserByEmail.get();
    }

    if (optionalDbUserByUsername.isPresent()) {
      return optionalDbUserByUsername.get();
    }

    return null;
  }
}
