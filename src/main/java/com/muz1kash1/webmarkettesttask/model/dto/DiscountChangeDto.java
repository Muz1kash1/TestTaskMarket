package com.muz1kash1.webmarkettesttask.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountChangeDto {
  private final float discountSize;
  private final LocalDate duration;
}
