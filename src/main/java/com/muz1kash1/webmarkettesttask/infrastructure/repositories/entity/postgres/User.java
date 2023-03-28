package com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "username")
  private String username;

  @Column(name = "mail")
  private String mail;

  @Column(name = "password")
  private String password;

  @Column(name = "balance")
  private BigDecimal balance;

  @Column(name = "enabled")
  private boolean enabled;
  @Column(name = "roles")
  private String roles;

  public User() {

  }

  public User(String username, String mail, String password, BigDecimal balance, boolean enabled, String roles) {
    this.username = username;
    this.mail = mail;
    this.password = password;
    this.balance = balance;
    this.enabled = enabled;
    this.roles = roles;
  }

}
