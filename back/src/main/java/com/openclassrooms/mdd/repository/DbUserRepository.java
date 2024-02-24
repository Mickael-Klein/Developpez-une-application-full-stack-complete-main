package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.DbUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing DbUser entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
public interface DbUserRepository extends JpaRepository<DbUser, Long> {
  /**
   * Find a user by their email.
   *
   * @param email The email of the user.
   * @return An Optional containing the user if found, empty otherwise.
   */
  Optional<DbUser> findByEmail(String email);

  /**
   * Find a user by their username.
   *
   * @param username The username of the user.
   * @return An Optional containing the user if found, empty otherwise.
   */
  Optional<DbUser> findByUsername(String username);
}
