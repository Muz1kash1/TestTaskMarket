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
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
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
      .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .headers(headers -> headers.frameOptions().sameOrigin())
      .authorizeHttpRequests()
      .requestMatchers(HttpMethod.POST, "/signup")
      .permitAll()
      .requestMatchers(HttpMethod.POST,"/signin")
      .permitAll()
      .requestMatchers(HttpMethod.POST,"/admin/signup")
      .permitAll()
      .requestMatchers(HttpMethod.DELETE, "/products/{id}")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.PUT, "/products/{id}/discounts/{discountId}")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.POST, "/products/{id}/discounts")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.GET, "/users/{userid}")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.DELETE, "/users/{userid}")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.PUT, "/users/{userid}/balance")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.PUT, "/users/{userid}/freeze")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.POST, "/users/{userid}/notifications")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.GET, "/users/{userid}/purchases")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.PUT, "/organisations/{id}/freeze")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.PUT, "/organisations/{id}/unfreeze")
      .hasAuthority("SCOPE_ADMIN,USER")
      .requestMatchers(HttpMethod.GET, "/purchases/{id}")
      .hasAuthority("SCOPE_ADMIN,USER")
      .anyRequest()
      .authenticated()
      .and()
      .httpBasic(
        Customizer.withDefaults()
      );
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
