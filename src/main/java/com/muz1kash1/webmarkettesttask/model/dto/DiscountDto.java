package com.muz1kash1.webmarkettesttask.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountDto {
  private final long id;
  private final Float discountSize;
  private final LocalDate durationOfDiscount;
}
