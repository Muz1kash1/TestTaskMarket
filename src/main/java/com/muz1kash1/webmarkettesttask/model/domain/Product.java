package com.muz1kash1.webmarkettesttask.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * Класс описывающий товар.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private long id;
  @NotEmpty
  private String name;
  private String description;
  @NotEmpty
  private String organisationName;
  @Min(value = 0, message = "товар не может стоить меньше 0")
  private BigDecimal price;
  @Min(value = 1, message = "Количество товара не может быть меньше 1")
  private Integer quantity;
  private List<String> keywords;
  private List<String> chars;
}
