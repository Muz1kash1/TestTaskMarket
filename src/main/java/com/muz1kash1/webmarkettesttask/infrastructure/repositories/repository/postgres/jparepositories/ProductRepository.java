package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query(value =
    """
      SELECT p.*
      FROM products p
               JOIN organisation_products op ON p.id = op.product_id
               JOIN organisations o ON op.organisation_id = o.id
      WHERE o.enabled != false;
      """
    , nativeQuery = true)
  List<Product> findAll();

  Optional<Product> findProductById(long id);

  Optional<Void> deleteById(long id);

  Optional<Product> findTopByOrderByIdDesc();

  @Query(value =
    """
          SELECT p.*
          FROM products p
                    JOIN purchases pu ON p.id = pu.product_id
           WHERE pu.user_id = :userId
           GROUP BY p.id;
      """
    ,
    nativeQuery = true)
  List<Product> findAllPurchasedProducts(@Param(value = "userId") long userId);
}
