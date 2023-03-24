package com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_notions")
public class UserNotions {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @Column(name = "user_id")
  private long userId;
  @Column(name = "notion_id")
  private long notionId;

  public UserNotions(long userId, long notionId) {
    this.userId = userId;
    this.notionId = notionId;
  }

  public UserNotions() {
  }
}
