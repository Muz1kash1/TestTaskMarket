package com.muz1kash1.webmarkettesttask.model.domain;

import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Класс описывающий уведомление. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notion {
  private long id;
  @NotEmpty private String header;
  private LocalDate notionDate;
  private String notionText;
}
