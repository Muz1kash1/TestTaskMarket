package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Organisation;
import com.muz1kash1.webmarkettesttask.model.dto.SignupOrganisationDto;

public interface IOrganisationRepo {
  Organisation freezeOrganisationById(long id);

  Organisation addOrganisationApplication(SignupOrganisationDto signOrganisationDto);

  Organisation unfreezeOrganisationById(long id);
}
