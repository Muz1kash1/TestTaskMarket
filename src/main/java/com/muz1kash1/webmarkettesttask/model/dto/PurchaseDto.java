package com.muz1kash1.webmarkettesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PurchaseDto {
  private final long id;
  private final long userId;
  private final long productId;
  private final boolean refunded;
  private final BigDecimal price;
  private final LocalDate purchaseDate;
}
