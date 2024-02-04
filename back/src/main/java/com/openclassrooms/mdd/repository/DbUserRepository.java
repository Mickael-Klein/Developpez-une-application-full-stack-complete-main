package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.model.DbUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbUserRepository extends JpaRepository<DbUser, Long> {
  Optional<DbUser> findByEmail(String email);
  Optional<DbUser> findByUsername(String username);
}
