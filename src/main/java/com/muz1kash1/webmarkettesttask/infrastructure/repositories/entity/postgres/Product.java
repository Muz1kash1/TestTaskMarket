package com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "description")
  private String description;

  @Column(name = "organisation_name")
  private String organisationName;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "keywords")
  private List<String> keywords;

  @Column(name = "chars")
  private List<String> chars;

  public Product(String productName, String description, String organisationName, BigDecimal price,
                 Integer quantity, List<String> keywords, List<String> chars) {
    this.productName = productName;
    this.description = description;
    this.organisationName = organisationName;
    this.price = price;
    this.quantity = quantity;
    this.keywords = keywords;
    this.chars = chars;
  }

  public Product() {
  }
}
