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

  @PostMapping("/register")
  public ResponseEntity<?> registerAccount(
    @Valid @RequestBody RegisterRequest registerRequest,
    BindingResult bindingResult
  ) {
    try {
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(
            dbUserAuthResponse.getRegisteringBadCredentialsResponseMessage()
          );
      }

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

      DbUser factoryUser = entityAndDtoCreation.getDbUserFromRegisterRequest(
        registerRequest
      );
      dbUserService.saveUser(factoryUser);

      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginAccount(
    @Valid @RequestBody LoginRequest loginRequest,
    BindingResult bindingResult
  ) {
    try {
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(dbUserAuthResponse.getLoginBadCredentialsResponseMessage());
      }

      DbUser dbUser = doesUserExist(loginRequest.getEmailOrUsername());

      if (dbUser == null) {
        return ResponseEntity
          .badRequest()
          .body(dbUserAuthResponse.getLoginBadCredentialsResponseMessage());
      }

      boolean isPasswordCorrect = dbUserService.isPasswordValid(
        loginRequest.getPassword(),
        dbUser
      );

      if (!isPasswordCorrect) {
        return ResponseEntity
          .badRequest()
          .body(dbUserAuthResponse.getLoginBadCredentialsResponseMessage());
      }

      String jwtToken = jwtService.generateToken(dbUser.getId());

      return ResponseEntity
        .ok()
        .body(dbUserAuthResponse.getJwtTokenResponseMessage(jwtToken));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  private DbUser doesUserExist(String emailOrUsername) {
    Optional<DbUser> optionalDbUser = dbUserService.getUserByEmail(
      emailOrUsername
    );
    Optional<DbUser> optionalDbUserByUsername = dbUserService.getUserByUsername(
      emailOrUsername
    );

    if (optionalDbUser.isPresent()) {
      return optionalDbUser.get();
    }

    if (optionalDbUserByUsername.isPresent()) {
      return optionalDbUserByUsername.get();
    }

    return null;
  }
}
