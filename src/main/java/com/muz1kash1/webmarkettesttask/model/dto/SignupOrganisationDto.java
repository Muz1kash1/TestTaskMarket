package com.muz1kash1.webmarkettesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupOrganisationDto {
  private final String organisationName;
  private final String organisationDescription;
  private final long logotypeId;

}
