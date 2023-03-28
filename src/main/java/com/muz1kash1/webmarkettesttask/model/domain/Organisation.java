package com.muz1kash1.webmarkettesttask.model.domain;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Класс описывающий организацию. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {
  private long id;
  @NotEmpty private String organisationName;
  private String organisationDescription;
  private long logotypeId;
  private boolean enabled;
  private long organisationOwnerId;
}
