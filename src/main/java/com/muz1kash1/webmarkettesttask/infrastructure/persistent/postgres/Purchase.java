package com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "purchases")
public class Purchase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @Column(name = "user_id")
  private long userId;
  @Column(name = "product_id")
  private long productId;
  @Column(name = "refunded")
  private boolean refunded;
  @Column(name = "price")
  private BigDecimal price;
  @Column(name = "purchase_date")
  private LocalDate purchaseDate;

  public Purchase(long userId, long productId, boolean refunded, BigDecimal price, LocalDate purchaseDate) {
    this.userId = userId;
    this.productId = productId;
    this.refunded = refunded;
    this.price = price;
    this.purchaseDate = purchaseDate;
  }

  public Purchase() {
  }
}
