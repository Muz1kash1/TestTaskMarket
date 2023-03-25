package com.muz1kash1.webmarkettesttask.infrastructure.service;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.IStoreRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Organisation;
import com.muz1kash1.webmarkettesttask.model.dto.OrganisationDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignupOrganisationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganisationService {
  IStoreRepo marketRepository;

  public OrganisationDto disableOrganisationById(final long id) {
    Organisation organisation = marketRepository.freezeOrganisationById(id);
    return new OrganisationDto(
      organisation.getId(),
      organisation.getOrganisationName(),
      organisation.getOrganisationDescription(),
      organisation.getLogotypeId(),
      organisation.isEnabled(),
      organisation.getOrganisationOwnerId()
    );
  }

  public OrganisationDto addOrganisationApplication(final SignupOrganisationDto signOrganisationDto) {
    Organisation organisation = marketRepository.addOrganisationApplication(signOrganisationDto);
    return new OrganisationDto(
      organisation.getId(),
      organisation.getOrganisationName(),
      organisation.getOrganisationDescription(),
      organisation.getLogotypeId(),
      organisation.isEnabled(),
      organisation.getOrganisationOwnerId()
    );
  }

  public OrganisationDto enableOrganisationById(final long id) {
    Organisation organisation = marketRepository.unfreezeOrganisationById(id);
    return new OrganisationDto(
      organisation.getId(),
      organisation.getOrganisationName(),
      organisation.getOrganisationDescription(),
      organisation.getLogotypeId(),
      organisation.isEnabled(),
      organisation.getOrganisationOwnerId()
    );
  }
}
