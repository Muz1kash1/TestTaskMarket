package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Purchase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
  Optional<Purchase> findTopByOrderByIdDesc();

  @Query(
    value = "SELECT * from purchases where user_id =:userId",
    nativeQuery = true
  )
  List<Purchase> findAllByUserId(@Param("userId") long userId);

}
