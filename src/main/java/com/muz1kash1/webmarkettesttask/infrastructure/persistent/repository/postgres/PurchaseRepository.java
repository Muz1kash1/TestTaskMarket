package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
  Optional<Purchase> findTopByOrderByIdDesc();
  @Modifying(clearAutomatically = true)
  @Transactional
  @Query(value =
    """
        
      INSERT INTO purchases (user_id, product_id, price, purchase_date)
               VALUES (:userId, 
                       :productId,
                       (select products.price * (1 - discounts.discount_size)
                       as discounted_price
                       from products join product_discounts on products.id = product_discounts.product_id
                       join discounts on product_discounts.discount_id = discounts.id
                       where products.id = :productId),
                       :purchaseDate) ;
         
      """

//       "SELECT TOP  id from purchases order by id desc;"
    ,
//
//    + "        UPDATE users SET balance = balance - (select products.price * (1 - discounts.discount_size) as discounted_price\n"
//    + "                                              from products join product_discounts on products.id = product_discounts.product_id\n"
//    + "                                                            join discounts on product_discounts.discount_id = discounts.id\n"
//    + "                                              where products.id = :productId) WHERE id = :userId;\n"
//    + "        "
//    +
//      "        UPDATE products SET quantity = quantity - 1 WHERE id = :productId;"
//    + "        SELECT * FROM purchases where id = 1;",
    nativeQuery = true)

  void savePurchase(@Param(value = "productId")long productId,@Param(value = "userId") long userId,@Param(value = "purchaseDate") LocalDate purchaseDate);
//  @Query(
//    value = """
//        INSERT INTO purchases (user_id, product_id, price, purchase_date)
//        VALUES (:userId, :productId,
//                (select products.price * (1 - discounts.discount_size) as discounted_price
//                from products join product_discounts on products.id = product_discounts.product_id
//                join discounts on product_discounts.discount_id = discounts.id
//                where products.id = :productId),
//                :dateOfPurchase);
//        UPDATE users SET balance = balance - (select products.price * (1 - discounts.discount_size) as discounted_price
//                                              from products join product_discounts on products.id = product_discounts.product_id
//                                                            join discounts on product_discounts.discount_id = discounts.id
//                                              where products.id = :productId) WHERE id = 1;
//
//        UPDATE products SET quantity = quantity - 1 WHERE id = :userId;
//        SELECT * FROM purchases where user_id = :userId and product_id = :productId and purchase_date = dateOfPurchase;
//      """,
//    nativeQuery = true
//  )



  //  void changeValues(@Param(value = "productId") int productId, @Param(value = "userId") int userId, @Param(value = "dateOfPurchase") LocalDate purchaseDate);
//  @Query(value = "SELECT * FROM purchases where user_id = :userId "
//    + "intersect "
//    + "select * from purchases where product_id = :productId ;"
//    + "intersect "
//    + "select * from purchases where purchase_date = :purchaseDate;",
//  nativeQuery = true)
}
