package com.openclassrooms.mdd.serviceInterface;

import com.openclassrooms.mdd.model.DbUser;
import java.util.Optional;

/**
 * Interface for managing DbUser entities.
 */
public interface DbUserInterface {
  /**
   * Saves a user.
   *
   * @param dbUser The user to save.
   * @return The saved user.
   */
  DbUser saveUser(DbUser dbUser);

  /**
   * Retrieves a user by email.
   *
   * @param email The email of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  Optional<DbUser> getUserByEmail(String email);

  /**
   * Retrieves a user by username.
   *
   * @param username The username of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  Optional<DbUser> getUserByUsername(String username);

  /**
   * Retrieves a user by ID.
   *
   * @param id The ID of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  Optional<DbUser> getUserById(long id);

  /**
   * Retrieves a user by ID with associated subjects eagerly fetched.
   *
   * @param id The ID of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  Optional<DbUser> getUserByIdWithSub(long id);

  /**
   * Checks if an email is already taken by another user.
   *
   * @param email The email to check.
   * @return True if the email is already taken, false otherwise.
   */
  boolean isEmailAlreadyTaken(String email);

  /**
   * Checks if a username is already taken by another user.
   *
   * @param username The username to check.
   * @return True if the username is already taken, false otherwise.
   */
  boolean isUsernameAlreadyTaken(String username);

  /**
   * Checks if a password matches the hashed password of a user.
   *
   * @param password The plain text password to check.
   * @param dbUser The user whose password should be checked.
   * @return True if the password is valid, false otherwise.
   */
  boolean isPasswordValid(String password, DbUser dbUser);
}
