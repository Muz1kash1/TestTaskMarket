package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.service.UserService;
import com.muz1kash1.webmarkettesttask.infrastructure.service.authentication.TokenService;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import com.muz1kash1.webmarkettesttask.model.dto.UserDto;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthenticationController {

  private final UserService userService;
  private final TokenService tokenService;

  public AuthenticationController(final UserService userService, final TokenService tokenService) {

    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping(value = "/signup")
  public ResponseEntity<UserDto> userSignup(@RequestBody @Valid SignUpDto signUpDto)throws ChangeSetPersister.NotFoundException {
    UserDto userDto = userService.signUp(signUpDto);
    return ResponseEntity.created(
            URI.create(
                "/users/"
                    + userService
                        .findByUsername(
                            userService.findByUsername(userDto.getUsername()).getUsername())
                        .getId()))
        .body(userDto);
  }

  @PostMapping("admin/signup")
  public ResponseEntity<UserDto> adminSignup(@RequestBody @Valid SignUpDto signUpDto)throws ChangeSetPersister.NotFoundException {
    UserDto userDto = userService.adminSignUp(signUpDto);
    return ResponseEntity.created(
            URI.create(
                "/users/"
                    + userService
                        .findByUsername(
                            userService.findByUsername(userDto.getUsername()).getUsername())
                        .getId()))
        .body(userDto);
  }

  @PostMapping(value = "/signin")
  public String userLogin(Authentication authentication) {
    log.info(authentication.getAuthorities().toString());
    log.info("token generated for '{}'", authentication.getName());
    String token = tokenService.generateToken(authentication);
    log.info("token granted {}", token);
    return token;
  }

  @GetMapping("/authorities")
  public Map<String, Object> getPrincipalInfo(JwtAuthenticationToken principal) {

    Collection<String> authorities =
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    Map<String, Object> info = new HashMap<>();
    info.put("name", principal.getName());
    info.put("authorities", authorities);
    info.put("tokenAttributes", principal.getTokenAttributes());

    return info;
  }
}
