package com.muz1kash1.webmarkettesttask.model.domain;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Класс описывающий пользователя. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private long id;
  @NotEmpty private String username;
  @NotEmpty private String mailAddress;
  @NotEmpty private String password;

  @Min(0)
  private BigDecimal balance;

  private boolean enabled;
  @NotEmpty private String roles;
}
