package com.muz1kash1.webmarkettesttask.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddReviewDto {
  private final long userId;
  private final String reviewText;
  private final int rating;
}
