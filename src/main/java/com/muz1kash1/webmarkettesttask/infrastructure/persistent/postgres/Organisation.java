package com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "organisations")
public class Organisation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "organisation_name")
  private String organisationName;
  @Column(name = "organisation_description")
  private String organisationDescription;

  @Column(name = "logotype_id")
  private long logotypeId;

  @Column(name = "enabled")
  private boolean enabled;

  @Column(name = "organisation_owner_id")
  private long organisationOwnerId;

  public Organisation(String organisationName, String organisationDescription, long logotypeId, boolean enabled,long organisationOwnerId) {
    this.organisationName = organisationName;
    this.organisationDescription = organisationDescription;
    this.logotypeId = logotypeId;
    this.enabled = enabled;
    this.organisationOwnerId = organisationOwnerId;
  }

  public Organisation() {
  }
}
