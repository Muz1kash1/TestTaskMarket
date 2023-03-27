package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductDiscountRepository extends JpaRepository<ProductDiscount,Long> {
  List<ProductDiscount> findByProductId(long productId);

}
