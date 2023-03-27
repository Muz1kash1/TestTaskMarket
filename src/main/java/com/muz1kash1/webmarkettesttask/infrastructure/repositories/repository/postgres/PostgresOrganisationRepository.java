package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Organisation;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IOrganisationRepo;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.UserRepository;
import com.muz1kash1.webmarkettesttask.model.dto.SignupOrganisationDto;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@ComponentScan("com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres")
@Repository
public class PostgresOrganisationRepository implements IOrganisationRepo {
  com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.OrganisationRepository
    organisationRepository;
  UserRepository userRepository;

  @Override
  public com.muz1kash1.webmarkettesttask.model.domain.Organisation freezeOrganisationById(final long id) {
    Organisation organisation = organisationRepository.getReferenceById(id);
    organisation.setEnabled(false);
    organisationRepository.save(organisation);
    return new com.muz1kash1.webmarkettesttask.model.domain.Organisation(
      organisation.getId(),
      organisation.getOrganisationName(),
      organisation.getOrganisationDescription(),
      organisation.getLogotypeId(),
      organisation.isEnabled(),
      organisation.getOrganisationOwnerId()
    );
  }

  @Override
  public com.muz1kash1.webmarkettesttask.model.domain.Organisation unfreezeOrganisationById(final long id) {
    Organisation organisation = organisationRepository.getReferenceById(id);
    organisation.setEnabled(true);
    organisationRepository.save(organisation);
    return new com.muz1kash1.webmarkettesttask.model.domain.Organisation(
      organisation.getId(),
      organisation.getOrganisationName(),
      organisation.getOrganisationDescription(),
      organisation.getLogotypeId(),
      organisation.isEnabled(),
      organisation.getOrganisationOwnerId()
    );
  }

  @Override
  public com.muz1kash1.webmarkettesttask.model.domain.Organisation addOrganisationApplication(
    final SignupOrganisationDto signOrganisationDto,
    String username) {
    User user = userRepository.findUserByUsername(username).get();
    Organisation organisation = new Organisation(
      signOrganisationDto.getOrganisationName(),
      signOrganisationDto.getOrganisationDescription(),
      signOrganisationDto.getLogotypeId(),
      false,
      user.getId()
    );
    organisationRepository.save(organisation);
    Organisation organisationToReturn = organisationRepository.findTopByOrderByIdDesc().get();
    return new com.muz1kash1.webmarkettesttask.model.domain.Organisation(
      organisationToReturn.getId(),
      organisationToReturn.getOrganisationName(),
      organisationToReturn.getOrganisationDescription(),
      organisationToReturn.getLogotypeId(),
      organisationToReturn.isEnabled(),
      organisationToReturn.getOrganisationOwnerId()
    );
  }
}
