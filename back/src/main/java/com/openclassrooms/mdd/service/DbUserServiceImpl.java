package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.repository.DbUserRepository;
import com.openclassrooms.mdd.serviceInterface.DbUserInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the DbUserInterface service.
 */
@Service
public class DbUserServiceImpl implements DbUserInterface {

  @Autowired
  private DbUserRepository dbUserRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  /**
   * Saves a user.
   *
   * @param dbUser The user to save.
   * @return The saved user.
   */
  @Override
  public DbUser saveUser(DbUser dbUser) {
    return dbUserRepository.save(dbUser);
  }

  /**
   * Retrieves a user by email.
   *
   * @param email The email of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  @Override
  public Optional<DbUser> getUserByEmail(String email) {
    return dbUserRepository.findByEmail(email);
  }

  /**
   * Retrieves a user by username.
   *
   * @param username The username of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  @Override
  public Optional<DbUser> getUserByUsername(String username) {
    return dbUserRepository.findByUsername(username);
  }

  /**
   * Retrieves a user by ID.
   *
   * @param id The ID of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  @Override
  public Optional<DbUser> getUserById(long id) {
    return dbUserRepository.findById(id);
  }

  /**
   * Retrieves a user by ID with associated subjects eagerly fetched.
   *
   * @param id The ID of the user to retrieve.
   * @return An Optional containing the user if found, empty otherwise.
   */
  @Override
  @Transactional
  public Optional<DbUser> getUserByIdWithSub(long id) {
    return dbUserRepository
      .findById(id)
      .map(dbUser -> {
        dbUser.getSubjects().size();
        return dbUser;
      });
  }

  /**
   * Checks if an email is already taken by another user.
   *
   * @param email The email to check.
   * @return True if the email is already taken, false otherwise.
   */
  @Override
  public boolean isEmailAlreadyTaken(String email) {
    Optional<DbUser> optionalUser = dbUserRepository.findByEmail(email);
    return optionalUser.isPresent();
  }

  /**
   * Checks if a username is already taken by another user.
   *
   * @param username The username to check.
   * @return True if the username is already taken, false otherwise.
   */
  @Override
  public boolean isUsernameAlreadyTaken(String username) {
    Optional<DbUser> optionalUser = dbUserRepository.findByUsername(username);
    return optionalUser.isPresent();
  }

  /**
   * Checks if a password matches the hashed password of a user.
   *
   * @param password The plain text password to check.
   * @param dbUser The user whose password should be checked.
   * @return True if the password is valid, false otherwise.
   */
  @Override
  public boolean isPasswordValid(String password, DbUser dbUser) {
    return passwordEncoder.matches(password, dbUser.getPassword());
  }
}
