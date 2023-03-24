package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.PostgresRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.service.UserService;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.PurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.UserDto;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class UserController {
  private final UserService userService;

  @GetMapping("/users/{userid}/purchases")
  public ResponseEntity<List<PurchaseDto>> getPurchaseHistoryForUser(@PathVariable long userid) {

    return null;
  }

  @PutMapping("/users/{userid}/balance")
  public ResponseEntity<UserDto> topUpUserBalance(@PathVariable long userid,
                                                  @RequestBody double value
                                                    ) {
    UserDto user = userService.changeUserBalance(userid, BigDecimal.valueOf(value));
    return ResponseEntity.ok().body(user);
  }

  @GetMapping("/users/{userid}")
  public ResponseEntity<UserDto> getUserById(@PathVariable long userid) {
    UserDto userDto = userService.getUserById(userid);
    return ResponseEntity.ok().body(userDto);
  }

  @DeleteMapping("/users/{userid}")
  public ResponseEntity<Void> deleteUser(@PathVariable long userid) {
    userService.deleteUserById(userid);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/users/{userid}/freeze")
  public ResponseEntity<UserDto> freezeUser(@PathVariable long userid) {
    UserDto userDto = userService.freezeUserById(userid);
    return ResponseEntity.ok().body(userDto);
  }

  @PostMapping("/users/{userid}/notifications")
  public ResponseEntity<NotionDto> sendNotificationToUser(@PathVariable long userid,
                                                          @RequestBody @Valid NotionDto notion) {
    NotionDto notionDto = userService.sendNotionToUser(userid,notion);
    return ResponseEntity.created(URI.create("/users" + String.valueOf(userid) + "/notifications/" + String.valueOf(notionDto.getId()))).body(notionDto);
  }

  @GetMapping("/users/{userid}/notifications")
  public ResponseEntity<List<NotionDto>> getNotification(@PathVariable long userid) {
    List<NotionDto> notionDtos = userService.getUserNotions(userid);
    return ResponseEntity.ok().body(notionDtos);
  }
}
