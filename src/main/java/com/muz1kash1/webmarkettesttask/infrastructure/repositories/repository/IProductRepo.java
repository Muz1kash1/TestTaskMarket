package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Discount;
import com.muz1kash1.webmarkettesttask.model.domain.Product;
import com.muz1kash1.webmarkettesttask.model.domain.Review;
import java.util.List;

public interface IProductRepo {
  Product getProductById(long id);
  Product addProduct(Product product, long organisationId);

  List<Product> getAllProducts();

  Product updateProductById(long id, Product product);

  void deleteProductById(long id);

  Discount changeDiscountToProduct(long productId, Discount discount);

  List<Product> getPurchasedProducts(final String username);

  Review addReview(Review review);

  Review updateProductAndReviewById(Review review);

  Review getReviewById(long reviewId);

  void deleteReviewById(long id, long reviewId);

  Long getIdOfLastReview();
}
