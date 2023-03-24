package com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product_reviews")
public class ProductReview {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "product_id")
  private long productId;

  @Column(name = "reviewtext")
  private String reviewText;

  @Column(name = "rating")
  private int rating;

  public ProductReview(long userId, long productId, String reviewText, int rating) {
    this.userId = userId;
    this.productId = productId;
    this.reviewText = reviewText;
    this.rating = rating;
  }

  public ProductReview() {
  }
}
