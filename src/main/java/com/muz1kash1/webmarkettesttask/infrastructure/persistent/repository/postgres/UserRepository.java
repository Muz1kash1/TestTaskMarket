package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserById(long id);

  Optional<User> findUserByUsername(String username);

  Optional<Void> deleteById(long id);

//  UPDATE User u SET u.balance = u.balance - (
//  SELECT p.price FROM Product p WHERE p.id = :productId
//        ) WHERE u.id = :userId
@Modifying
@Query(
  value =
    """
        UPDATE User u
                       SET u.balance = u.balance - (
                            SELECT p.price * COALESCE((
                                    SELECT pd.multiplier
                                    FROM product_discounts pd
                                    JOIN discounts d ON pd.discount_id = d.id
                                    WHERE pd.product_id = :productId
                                      AND :orderAmount >= d.threshold
                                    ORDER BY d.threshold DESC
                                    LIMIT 1
                                ), 1) * (1 - d.discount_size)
                                FROM Product p
                                JOIN p.discounts d
                                WHERE p.id = :productId
                                  AND :orderAmount >= d.threshold
                            )
                            WHERE u.id = :userId

        
        
      """
  //UPDATE User u SET u.balance = case when(u.id = :userId) then (u.balance - (
  //          select p.price * (1 - d.discountSize)
  //          FROM Product p
  //          JOIN p.discounts d
  //          WHERE p.id = :productId
  //        ) ) end

  //SELECT u FROM User u WHERE u.id = :userId

//          UPDATE users SET balance = balance - (select products.price * (1 - discounts.discount_size) as discounted_price
//          from products join product_discounts on products.id = product_discounts.product_id
//          join discounts on product_discounts.discount_id = discounts.id
//          where products.id = :productId) WHERE id = :userId;
//
//          SELECT * from users where id = :userId
//
//    , nativeQuery = true
)
void decrementUserBalanceByProductCost(@Param(value = "userId") long userId,
                                       @Param(value = "productId") long productId);

//

}
