package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Discount;
import com.muz1kash1.webmarkettesttask.model.domain.Notion;
import com.muz1kash1.webmarkettesttask.model.domain.Product;
import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.domain.Review;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс репозитория для работы с базой данных
 */
public interface IStoreRepo {
  User getUserById(long id);
  void deleteUserById(long id);
  User disableUser(long id);
  User changeUserBalance(long id, BigDecimal value);

  Product getProductById(long id);
  Product addProduct(Product product, long organisationId);

  Notion sendNotionToUser(long userid, NotionDto notionDto);

  User addUser(SignUpDto signUpDto);

  User getUserByUsername(String username);

  List<Notion> getNotionsOfUser(long userid);

  List<Product> getAllProducts();

  Product updateProductById(long id, Product product);

  void deleteProductById(long id);

  Discount changeDiscountToProduct(long productId, Discount discount);

  List<Product> getPurchasedProducts(long userId);

  Review addReview(Review review);

  Review updateProductAndReviewById(Review review);

  Review getReviewById(long reviewId);

  void deleteReviewById(long id, long reviewId);

  Purchase addPurchase(MakePurchaseDto makePurchaseDto);
}
