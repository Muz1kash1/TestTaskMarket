package com.muz1kash1.webmarkettesttask.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SignUpDto {
  @NotEmpty
  private final String username;
  @NotEmpty
  private final String mail;
  @NotEmpty
  private final String password;
}
