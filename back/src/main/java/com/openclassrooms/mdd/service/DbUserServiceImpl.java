package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.repository.DbUserRepository;
import com.openclassrooms.mdd.serviceInterface.DbUserInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DbUserServiceImpl implements DbUserInterface {

  @Autowired
  private DbUserRepository dbUserRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public DbUser saveUser(DbUser dbUser) {
    return dbUserRepository.save(dbUser);
  }

  @Override
  public Optional<DbUser> getUserByEmail(String email) {
    return dbUserRepository.findByEmail(email);
  }

  @Override
  public Optional<DbUser> getUserByUsername(String username) {
    return dbUserRepository.findByUsername(username);
  }

  @Override
  public Optional<DbUser> getUserById(long id) {
    return dbUserRepository.findById(id);
  }

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

  @Override
  public boolean isEmailAlreadyTaken(String email) {
    Optional<DbUser> optionalUser = dbUserRepository.findByEmail(email);
    return optionalUser.isPresent();
  }

  @Override
  public boolean isUsernameAlreadyTaken(String username) {
    Optional<DbUser> optionalUser = dbUserRepository.findByUsername(username);
    return optionalUser.isPresent();
  }

  @Override
  public boolean isPasswordValid(String password, DbUser dbUser) {
    return passwordEncoder.matches(password, dbUser.getPassword());
  }
}
