package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.DbUser;
import java.util.Optional;

public interface DbUserInterface {
  DbUser saveUser(DbUser dbUser);

  Optional<DbUser> getUserByEmail(String email);

  Optional<DbUser> getUserById(long id);

  Boolean isEmailAlreadyTaken(String email);

  Boolean isPasswordValid(String password, DbUser dbUser);
}
