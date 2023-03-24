package com.muz1kash1.webmarkettesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReviewDto {
  private final long id;
  private final long userId;
  private final long productId;
  private final String reviewText;
  private final int rating;
}
