package com.muz1kash1.webmarkettesttask.config;

import com.muz1kash1.webmarkettesttask.infrastructure.service.JpaUserDetailsService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class JpaSecurityConfig {
  private final JpaUserDetailsService jpaUserDetailsService;
  private final RsaKeyProperties rsaKeyProperties;

  @Bean
  @Order(1)
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .userDetailsService(jpaUserDetailsService)
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//      .headers(headers -> headers.frameOptions().sameOrigin())
      .authorizeHttpRequests()
      .requestMatchers("/signup", "/signin").permitAll()
      .requestMatchers(HttpMethod.POST, "/products")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/products/**")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.DELETE, "/products/{id}")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/products/{id}/discounts/{discountId}")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.POST, "/products/{id}/discounts")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.GET, "/users/{userid}")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.DELETE, "/users/{userid}")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/users/{userid}/balance")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/users/{userid}/freeze")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.POST, "/users/{userid}/notifications")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.GET, "/users/{userid}/purchases")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/organisations/{id}/freeze")
      .hasAuthority("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/organisations/{id}/unfreeze")
      .hasAuthority("ADMIN")
      .anyRequest().authenticated()
      .and()
      .httpBasic();
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
  }

  @Bean JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwkSource);
  }
}
