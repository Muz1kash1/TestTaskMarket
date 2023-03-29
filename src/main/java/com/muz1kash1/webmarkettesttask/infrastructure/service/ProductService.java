package com.muz1kash1.webmarkettesttask.infrastructure.service;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IProductRepo;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IUserRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Discount;
import com.muz1kash1.webmarkettesttask.model.domain.Product;
import com.muz1kash1.webmarkettesttask.model.domain.Review;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.AddProductDto;
import com.muz1kash1.webmarkettesttask.model.dto.AddReviewDto;
import com.muz1kash1.webmarkettesttask.model.dto.DiscountChangeDto;
import com.muz1kash1.webmarkettesttask.model.dto.DiscountDto;
import com.muz1kash1.webmarkettesttask.model.dto.ProductDto;
import com.muz1kash1.webmarkettesttask.model.dto.ReviewDto;
import com.muz1kash1.webmarkettesttask.model.dto.UpdateProductDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class ProductService {
  private final IProductRepo productRepository;
  private final IUserRepo userRepository;

  public List<ProductDto> getAllProducts() {
    List<Product> products = productRepository.getAllProducts();
    List<ProductDto> productDtos = new ArrayList<>();
    for (Product product : products) {
      productDtos.add(
          new ProductDto(
              product.getId(),
              product.getName(),
              product.getDescription(),
              product.getOrganisationName(),
              product.getPrice(),
              product.getQuantity(),
              product.getKeywords(),
              product.getChars()));
    }
    return productDtos;
  }

  public ProductDto getProductById(final long id) {
    Product product = productRepository.getProductById(id);
    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars());
  }

  public ProductDto addNewProduct(final AddProductDto addProductDto, String username) {
    Product product =
        productRepository.addProduct(
            new Product(
                1,
                addProductDto.getName(),
                addProductDto.getDescription(),
                addProductDto.getOrganisationName(),
                addProductDto.getPrice(),
                addProductDto.getQuantity(),
                addProductDto.getKeywords(),
                addProductDto.getChars()),
            addProductDto.getOrganisationId(),
            username);
    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars());
  }

  public ProductDto updateProduct(final long id, final UpdateProductDto updateProductDto) {
    Product product = productRepository.getProductById(id);
    product.setName(updateProductDto.getName());
    product.setDescription(updateProductDto.getDescription());
    product.setOrganisationName(updateProductDto.getOrganisationName());
    product.setQuantity(updateProductDto.getQuantity());
    product.setChars(updateProductDto.getChars());
    product.setPrice(updateProductDto.getPrice());
    product.setKeywords(updateProductDto.getKeywords());

    Product updatedProduct = productRepository.updateProductById(id, product);
    return new ProductDto(
        updatedProduct.getId(),
        updateProductDto.getName(),
        updatedProduct.getDescription(),
        updatedProduct.getOrganisationName(),
        updatedProduct.getPrice(),
        updatedProduct.getQuantity(),
        updatedProduct.getKeywords(),
        updatedProduct.getChars());
  }

  public void deleteProduct(final long id) {
    productRepository.deleteProductById(id);
  }

  public DiscountDto changeDiscountToProduct(
      final long productId, final DiscountChangeDto discountChangeDto) {
    Discount discount =
        new Discount(1, discountChangeDto.getDiscountSize(), discountChangeDto.getDuration());
    Discount discountToReturn = productRepository.changeDiscountToProduct(productId, discount);
    return new DiscountDto(
        discountToReturn.getId(),
        discountToReturn.getDiscountSize(),
        discountToReturn.getDurationOfDiscount());
  }

  public ReviewDto addNewReviewForPurchasedProduct(
      final long id, final AddReviewDto addReviewDto, String username) {
    com.muz1kash1.webmarkettesttask.model.domain.Review review;

    List<Product> products = productRepository.getPurchasedProducts(username);
    if (products.stream().anyMatch(o -> o.getId() == id)) {

      review =
          productRepository.addReview(
              new Review(
                  productRepository.getIdOfLastReview(),
                  userRepository.getUserByUsername(username).getId(),
                  id,
                  addReviewDto.getReviewText(),
                  addReviewDto.getRating()));
    } else {
      throw new RuntimeException("Нельзя оставлять отзыв не купив товар");
    }
    return new ReviewDto(
        review.getId(),
        review.getUserId(),
        review.getProductId(),
        review.getReviewText(),
        review.getRating());
  }

  public ReviewDto updateExistingReview(
      final long id, final long reviewId, final AddReviewDto addReviewDto, String username) {
    User user = userRepository.getUserByUsername(username);
    Review review =
        new Review(
            reviewId, user.getId(), id, addReviewDto.getReviewText(), addReviewDto.getRating());
    Review reviewForCheck = productRepository.getReviewById(reviewId);
    if (reviewForCheck.getUserId() == user.getId()) {
      Review reviewToUpdate = productRepository.updateProductAndReviewById(review);
      return new ReviewDto(
          reviewToUpdate.getId(),
          reviewToUpdate.getUserId(),
          reviewToUpdate.getProductId(),
          reviewToUpdate.getReviewText(),
          reviewToUpdate.getRating());
    } else {
      throw new RuntimeException("Только тот кто оставил ревью может его менять");
    }
  }

  public void deleteReviewById(final long id, final long reviewId, String username) {
    User user = userRepository.getUserByUsername(username);
    Review review = productRepository.getReviewById(reviewId);
    if (review.getUserId() == user.getId()){
      productRepository.deleteReviewById(id, reviewId);
    }
    else throw new RuntimeException("Удалять ревью может только тот кто его оставил");
  }

  public ProductDto addProductToOrganisationProducts(long id) {
    Product product = productRepository.enableOrganisationProduct(id);
    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars());
  }
}
