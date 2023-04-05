package com.muz1kash1.webmarkettesttask.infrastructure.service;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IOrganisationRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Organisation;
import com.muz1kash1.webmarkettesttask.model.dto.OrganisationDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignupOrganisationDto;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class OrganisationService {
  IOrganisationRepo organisationRepository;

  public OrganisationDto disableOrganisationById(final long id) {
    Organisation organisation = organisationRepository.freezeOrganisationById(id);
    return new OrganisationDto(
        organisation.getId(),
        organisation.getOrganisationName(),
        organisation.getOrganisationDescription(),
        organisation.getLogotypeId(),
        organisation.isEnabled(),
        organisation.getOrganisationOwnerId());
  }

  public OrganisationDto addOrganisationApplication(
      final SignupOrganisationDto signOrganisationDto, String username)throws ChangeSetPersister.NotFoundException {
    Organisation organisation =
        organisationRepository.addOrganisationApplication(signOrganisationDto, username);
    return new OrganisationDto(
        organisation.getId(),
        organisation.getOrganisationName(),
        organisation.getOrganisationDescription(),
        organisation.getLogotypeId(),
        organisation.isEnabled(),
        organisation.getOrganisationOwnerId());
  }

  public OrganisationDto enableOrganisationById(final long id) {
    Organisation organisation = organisationRepository.unfreezeOrganisationById(id);
    return new OrganisationDto(
        organisation.getId(),
        organisation.getOrganisationName(),
        organisation.getOrganisationDescription(),
        organisation.getLogotypeId(),
        organisation.isEnabled(),
        organisation.getOrganisationOwnerId());
  }
}
