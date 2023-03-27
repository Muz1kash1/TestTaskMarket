package com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "notions")
public class Notion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @Column(name = "header")
  private String header;
  @Column(name = "notion_date")
  private LocalDate notionDate;
  @Column(name = "notion_text")
  private String notionText;

  public Notion(String header, LocalDate notionDate, String notionText) {
    this.header = header;
    this.notionDate = notionDate;
    this.notionText = notionText;
  }

  public Notion() {
  }
}
