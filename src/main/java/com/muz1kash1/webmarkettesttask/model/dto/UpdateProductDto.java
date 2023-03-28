package com.muz1kash1.webmarkettesttask.model.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductDto {
  private final String name;
  private final String description;

  private final String organisationName;
  private final BigDecimal price;
  private final Integer quantity;
  private final List<String> keywords;
  private final List<String> chars;
}
