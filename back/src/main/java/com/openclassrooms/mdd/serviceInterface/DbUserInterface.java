package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.DbUser;
import java.util.Optional;

public interface DbUserInterface {
  DbUser saveUser(DbUser dbUser);

  Optional<DbUser> getUserByEmail(String email);

  Optional<DbUser> getUserByUsername(String username);

  Optional<DbUser> getUserById(long id);

  Optional<DbUser> getUserByIdWithSub(long id);

  boolean isEmailAlreadyTaken(String email);

  boolean isUsernameAlreadyTaken(String username);

  boolean isPasswordValid(String password, DbUser dbUser);
}
