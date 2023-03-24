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
@Table(name = "organisation_products")
public class OrganisationProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "organisation_id")
  private long organisationId;

  @Column(name = "product_id")
  private long productId;

  private OrganisationProduct() {
  }

  public OrganisationProduct(long organisationId, long productId) {
    this.organisationId = organisationId;
    this.productId = productId;
  }
}
