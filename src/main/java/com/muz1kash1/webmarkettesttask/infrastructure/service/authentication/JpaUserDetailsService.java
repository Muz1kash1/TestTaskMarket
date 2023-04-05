package com.muz1kash1.webmarkettesttask.infrastructure.service.authentication;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.UserRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.service.authentication.userdetails.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@AllArgsConstructor
@Service
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) {
    return userRepository
        .findUserByUsername(username)
        .map(SecurityUser::new)
        .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND));
  }
}
