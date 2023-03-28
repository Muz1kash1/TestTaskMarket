package com.muz1kash1.webmarkettesttask.model.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {
  @NotEmpty private final String username;
  @NotEmpty private final String mail;
  @NotEmpty private final String password;
}
