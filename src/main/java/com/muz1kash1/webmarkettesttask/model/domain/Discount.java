package com.muz1kash1.webmarkettesttask.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * Класс описывающий скидку.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
  private long id;
  @Max(value = 1, message = "Скидка не может быть больше 100%")
  @Min(value = 0, message = "Скидка не может быть отрицательной")
  private Float discountSize;
  private LocalDate durationOfDiscount;
}
