package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.service.ProductService;
import com.muz1kash1.webmarkettesttask.model.dto.AddProductDto;
import com.muz1kash1.webmarkettesttask.model.dto.AddReviewDto;
import com.muz1kash1.webmarkettesttask.model.dto.DiscountChangeDto;
import com.muz1kash1.webmarkettesttask.model.dto.DiscountDto;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.ProductDto;
import com.muz1kash1.webmarkettesttask.model.dto.PurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.ReviewDto;
import com.muz1kash1.webmarkettesttask.model.dto.UpdateProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/products")
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> productDtos = productService.getAllProducts();
    return ResponseEntity.ok().body(productDtos);

  }

  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
    ProductDto productDto = productService.getProductById(id);
    return ResponseEntity.ok().body(productDto);
  }

  @PostMapping("/products")
  public ResponseEntity<ProductDto> addProduct(@RequestBody AddProductDto addProductDto) {
    ProductDto productDto = productService.addNewProduct(addProductDto);
    return ResponseEntity.created(URI.create("/products/" + productDto.getId())).body(productDto);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable long id,
                                                  @RequestBody UpdateProductDto updateProductDto) {
    ProductDto productDto = productService.updateProduct(id, updateProductDto);
    return ResponseEntity.ok().body(productDto);
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/products/{id}/discounts")
  public ResponseEntity<DiscountDto> updateDiscount(@PathVariable long id,
                                                    @RequestBody DiscountChangeDto discountChangeDto) {
    DiscountDto discountDto = productService.changeDiscountToProduct(id, discountChangeDto);
    return ResponseEntity.ok(discountDto);
  }

  @PostMapping("/products/{id}/reviews")
  public ResponseEntity<ReviewDto> addNewReviewForPurchasedProduct(@PathVariable long id,
                                                                   @RequestBody AddReviewDto addReviewDto) {
    return ResponseEntity.ok().body(productService.addNewReviewForPurchasedProduct(id, addReviewDto));

  }

  @PutMapping("/products/{id}/reviews/{reviewId}")
  public ResponseEntity<ReviewDto> updateReview(@PathVariable long id
    , @PathVariable long reviewId
    , @RequestBody AddReviewDto addReviewDto) {
    return ResponseEntity.ok().body(productService.udateExistingReview(id, reviewId, addReviewDto));
  }

  @DeleteMapping("/products/{id}/reviews/{reviewId}")
  public ResponseEntity<Void> deleteReview(@PathVariable long id,
                                           @PathVariable long reviewId) {
    productService.deleteReviewById(id,reviewId);
    return ResponseEntity.noContent().build();
  }

}
