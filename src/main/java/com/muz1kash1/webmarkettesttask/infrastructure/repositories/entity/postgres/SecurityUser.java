package com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class SecurityUser implements UserDetails {
  private User user;

  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    for (String role : user.getRoles().split(",")) {
      SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role);
      authorities.add(sga);
    }
    return authorities;
  }

  @Override public String getPassword() {
    return user.getPassword();
  }

  @Override public String getUsername() {
    return user.getUsername();
  }

  @Override public boolean isAccountNonExpired() {
    return true;
  }

  @Override public boolean isAccountNonLocked() {
    return true;
  }

  @Override public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override public boolean isEnabled() {
    return user.isEnabled();
  }
}
