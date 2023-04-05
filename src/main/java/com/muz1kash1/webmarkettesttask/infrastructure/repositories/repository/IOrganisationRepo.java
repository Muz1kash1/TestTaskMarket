package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Organisation;
import com.muz1kash1.webmarkettesttask.model.dto.SignupOrganisationDto;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface IOrganisationRepo {
  Organisation freezeOrganisationById(long id);

  Organisation addOrganisationApplication(
      SignupOrganisationDto signOrganisationDto, String username)
      throws ChangeSetPersister.NotFoundException;

  Organisation unfreezeOrganisationById(long id);
}
