package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Discount;
import com.muz1kash1.webmarkettesttask.model.domain.Product;
import com.muz1kash1.webmarkettesttask.model.domain.Review;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface IProductRepo {
  Product getProductById(long id)throws ChangeSetPersister.NotFoundException;
  Product addProduct(Product product, long organisationId, String username)throws ChangeSetPersister.NotFoundException;

  List<Product> getAllProducts();

  Product updateProductById(long id, Product product);

  void deleteProductById(long id);

  Discount changeDiscountToProduct(long productId, Discount discount);

  List<Product> getPurchasedProducts(final String username)throws ChangeSetPersister.NotFoundException;

  Review addReview(Review review)throws ChangeSetPersister.NotFoundException;

  Review updateProductAndReviewById(Review review)throws ChangeSetPersister.NotFoundException;

  Review getReviewById(long reviewId);

  void deleteReviewById(long id, long reviewId);

  Long getIdOfLastReview()throws ChangeSetPersister.NotFoundException;

  Product enableOrganisationProduct(long id)throws ChangeSetPersister.NotFoundException;
}
