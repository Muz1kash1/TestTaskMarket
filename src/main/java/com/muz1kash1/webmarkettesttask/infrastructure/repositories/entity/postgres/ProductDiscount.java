package com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "product_discounts")
@Data
@Entity
public class ProductDiscount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "product_id")
  private long productId;

  @Column(name = "discount_id")
  private long discountId;

  public ProductDiscount(){

  }
  public ProductDiscount(long productId, long discountId){
    this.productId = productId;
    this.discountId = discountId;
  }


}
