package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.service.TokenService;
import com.muz1kash1.webmarkettesttask.infrastructure.service.UserService;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import com.muz1kash1.webmarkettesttask.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.net.URI;

@RestController
@Slf4j
public class AuthenticationController {
  private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
  private final UserService userService;
  private final TokenService tokenService;


  public AuthenticationController(
    final InMemoryUserDetailsManager inMemoryUserDetailsManager, final UserService userService,
    final TokenService tokenService) {
    this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;

    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping(value = "/signup")
  public ResponseEntity<UserDto> userSignup(@RequestBody @Valid SignUpDto signUpDto) {
    UserDto userDto = userService.signUp(signUpDto);
    inMemoryUserDetailsManager.createUser(User
      .withUsername(userDto.getUsername())
      .password("{noop}" + userDto.getPassword())
      .roles("USER")
      .build()); // Костыль вместо авторизационной базы
    return ResponseEntity
      .created(URI.create(
        "/users/" + userService
          .findByUsername(userService.findByUsername(userDto.getUsername()).getUsername())
          .getId()))
      .body(userDto);
  }

  @PostMapping(value = "/signin")
  public String userLogin(Authentication authentication) {
    log.info("token generated for '{}'", authentication.getName());
    String token = tokenService.generateToken(authentication);
    log.info("token granted {}", token);
    return token;
  }
}
