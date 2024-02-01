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

@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private DbUserRepository dbUserRepository;

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    Optional<DbUser> optionalUser = dbUserRepository.findByEmail(email);

    DbUser dbUser = optionalUser.orElseThrow(() ->
      new UsernameNotFoundException("User not found with email: " + email)
    );

    return new User(dbUser.getEmail(), dbUser.getPassword(), null);
  }
}
