package com.muz1kash1.webmarkettesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrganisationDto {
  private final long id;
  private final String organisationName;
  private final String organisationDescription;
  private final long logotypeId;
  private final boolean enabled;
  private final long organisationOwnerId;
}
