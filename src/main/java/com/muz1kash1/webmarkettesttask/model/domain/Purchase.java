package com.muz1kash1.webmarkettesttask.model.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
