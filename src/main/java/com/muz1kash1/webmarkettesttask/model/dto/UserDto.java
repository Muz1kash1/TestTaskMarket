package com.muz1kash1.webmarkettesttask.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
  private final long id;
  private final String username;
  private final String mail;
  private final BigDecimal balance;
  private final String password;
  private final boolean enabled;

  private final String roles;
}
