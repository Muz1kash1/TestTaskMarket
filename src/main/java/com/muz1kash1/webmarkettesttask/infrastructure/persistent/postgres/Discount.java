package com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "discounts")
public class Discount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "discount_size")
  private float discountSize;

  @Column(name = "duration")
  private LocalDate duration;

  public Discount(float discountSize, LocalDate duration) {
    this.discountSize = discountSize;
    this.duration = duration;
  }

  public Discount() {
  }
}
