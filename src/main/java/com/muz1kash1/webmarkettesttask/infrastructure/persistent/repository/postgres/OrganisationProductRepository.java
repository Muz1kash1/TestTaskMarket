package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.OrganisationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationProductRepository extends JpaRepository<OrganisationProduct, Long> {
  void deleteByProductId(long id);

  OrganisationProduct findByProductId(long productId);

}
