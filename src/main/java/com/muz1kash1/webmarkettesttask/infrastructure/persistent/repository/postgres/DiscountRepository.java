package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Discount;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
  Discount findTopByOrderByIdDesc();
}
