package com.muz1kash1.webmarkettesttask.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
  private long id;
  private long userId;
  private long productId;
  private boolean refunded;
  private BigDecimal price;
  private LocalDate purchaseDate;
}
