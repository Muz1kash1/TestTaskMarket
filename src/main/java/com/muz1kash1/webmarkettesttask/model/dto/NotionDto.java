package com.muz1kash1.webmarkettesttask.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotionDto {
  private final long id;
  private final String header;
  private final LocalDate notionDate;
  private final String notionText;
}
