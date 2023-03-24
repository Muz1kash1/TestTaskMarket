package com.muz1kash1.webmarkettesttask.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * Класс описывающий уведомление.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notion {
  private long id;
  @NotEmpty
  private String header;
  private LocalDate notionDate;
  private String notionText;
}
