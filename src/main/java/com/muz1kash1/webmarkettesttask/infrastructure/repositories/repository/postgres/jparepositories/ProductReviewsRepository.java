package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ProductReviewsRepository extends JpaRepository<ProductReview, Long> {
  Optional<ProductReview> findTopByOrderByIdDesc();
}
