package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.service.OrganisationService;
import com.muz1kash1.webmarkettesttask.model.dto.OrganisationDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignupOrganisationDto;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class OrganisationController {
  private final OrganisationService organisationService;

  @PutMapping("/organisations/{id}/unfreeze")
  public ResponseEntity<OrganisationDto> unfreezeOrganisation(@PathVariable long id) {
    return ResponseEntity.ok().body(organisationService.enableOrganisationById(id));
  }

  @PutMapping("/organisations/{id}/freeze")
  public ResponseEntity<OrganisationDto> freezeOrganisation(@PathVariable long id) {
    return ResponseEntity.ok().body(organisationService.disableOrganisationById(id));
  }

  @PostMapping("/organisations")
  public ResponseEntity<OrganisationDto> addOrganisationApplication(
      @RequestBody SignupOrganisationDto signOrganisationDto, JwtAuthenticationToken principal)
      throws ChangeSetPersister.NotFoundException {
    OrganisationDto organisationDto =
        organisationService.addOrganisationApplication(signOrganisationDto, principal.getName());
    return ResponseEntity.created(URI.create("/organisations/" + organisationDto.getId()))
        .body(organisationDto);
  }
}
