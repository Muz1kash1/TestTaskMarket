package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.service.ProductService;
import com.muz1kash1.webmarkettesttask.model.dto.AddProductDto;
import com.muz1kash1.webmarkettesttask.model.dto.AddReviewDto;
import com.muz1kash1.webmarkettesttask.model.dto.DiscountChangeDto;
import com.muz1kash1.webmarkettesttask.model.dto.DiscountDto;
import com.muz1kash1.webmarkettesttask.model.dto.ProductDto;
import com.muz1kash1.webmarkettesttask.model.dto.ReviewDto;
import com.muz1kash1.webmarkettesttask.model.dto.UpdateProductDto;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/products")
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> productDtos = productService.getAllProducts();
    return ResponseEntity.ok().body(productDtos);
  }

  @PostMapping("/products")
  public ResponseEntity<ProductDto> addProduct(
      @RequestBody AddProductDto addProductDto, JwtAuthenticationToken principal) {
    ProductDto productDto = productService.addNewProduct(addProductDto, principal.getName());
    return ResponseEntity.created(URI.create("/products/" + productDto.getId())).body(productDto);
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
    ProductDto productDto = productService.getProductById(id);
    return ResponseEntity.ok().body(productDto);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<ProductDto> updateProduct(
      @PathVariable long id, @RequestBody UpdateProductDto updateProductDto) {
    ProductDto productDto = productService.updateProduct(id, updateProductDto);
    return ResponseEntity.ok().body(productDto);
  }

  @PutMapping("/products/{id}/enable")
  public ResponseEntity<ProductDto> addProductToOrganisationProducts(@PathVariable long id) {
    ProductDto productDto = productService.addProductToOrganisationProducts(id);
    return ResponseEntity.ok().body(productDto);
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/products/{id}/discounts")
  public ResponseEntity<DiscountDto> updateDiscount(
      @PathVariable long id, @RequestBody DiscountChangeDto discountChangeDto) {
    DiscountDto discountDto = productService.changeDiscountToProduct(id, discountChangeDto);
    return ResponseEntity.ok(discountDto);
  }

  @PostMapping("/products/{id}/reviews")
  public ResponseEntity<ReviewDto> addNewReviewForPurchasedProduct(
      @PathVariable long id,
      @RequestBody AddReviewDto addReviewDto,
      JwtAuthenticationToken principal) {
    return ResponseEntity.ok()
        .body(
            productService.addNewReviewForPurchasedProduct(id, addReviewDto, principal.getName()));
  }

  @PutMapping("/products/{id}/reviews/{reviewId}")
  public ResponseEntity<ReviewDto> updateReview(
      @PathVariable long id,
      @PathVariable long reviewId,
      @RequestBody AddReviewDto addReviewDto,
      JwtAuthenticationToken principal) {
    return ResponseEntity.ok()
        .body(productService.updateExistingReview(id, reviewId, addReviewDto, principal.getName()));
  }

  @DeleteMapping("/products/{id}/reviews/{reviewId}")
  public ResponseEntity<Void> deleteReview(@PathVariable long id, @PathVariable long reviewId) {
    productService.deleteReviewById(id, reviewId);
    return ResponseEntity.noContent().build();
  }
}
