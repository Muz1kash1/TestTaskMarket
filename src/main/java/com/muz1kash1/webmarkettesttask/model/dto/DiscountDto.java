package com.muz1kash1.webmarkettesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DiscountDto {
  private final long id;
  private final Float discountSize;
  private final LocalDate durationOfDiscount;
}
