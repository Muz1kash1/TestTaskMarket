package com.muz1kash1.webmarkettesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DiscountChangeDto {
  private final float discountSize;
  private final LocalDate duration;
}
