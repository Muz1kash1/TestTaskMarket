package com.muz1kash1.webmarkettesttask.model.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Review {
  private long id;
  private long userId;
  private long productId;
  private String reviewText;

  @Min(0)
  @Max(10)
  private int rating;
}
