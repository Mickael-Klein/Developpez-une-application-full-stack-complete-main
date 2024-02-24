package com.openclassrooms.mdd.configuration;

import com.openclassrooms.mdd.model.DbUser;
import com.openclassrooms.mdd.repository.DbUserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of UserDetailsService to load user details from the database.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private DbUserRepository dbUserRepository;

  /**
   * Load user details by email.
   *
   * @param email the email of the user
   * @return UserDetails object containing user details
   * @throws UsernameNotFoundException if user with the given email is not found
   */
  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    Optional<DbUser> optionalUser = dbUserRepository.findByEmail(email);

    DbUser dbUser = optionalUser.orElseThrow(() ->
      new UsernameNotFoundException("User not found with email: " + email)
    );

    // Returning UserDetails object with user's email and password
    return new User(dbUser.getEmail(), dbUser.getPassword(), null);
  }
}
