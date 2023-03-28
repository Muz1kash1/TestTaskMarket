package com.muz1kash1.webmarkettesttask.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MakePurchaseDto {
  private final long productId;
  private final LocalDate purchaseDate;
}
