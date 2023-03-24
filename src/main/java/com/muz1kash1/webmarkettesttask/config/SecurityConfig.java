package com.muz1kash1.webmarkettesttask.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final RsaKeyProperties rsaKeyProperties;


  public SecurityConfig(final RsaKeyProperties rsaKeyProperties) {
    this.rsaKeyProperties = rsaKeyProperties;

  }

  @Bean
  public InMemoryUserDetailsManager users() {
    final Properties users = new Properties();
    users.put("user", "{noop}password,USER,enabled");
    InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(users);
    inMemoryUserDetailsManager.createUser(User.withUsername("admin")
      .password("{noop}adminpassword").roles("ADMIN", "USER")
      .build());
    return inMemoryUserDetailsManager;

  }

  @Bean
  @Order(1)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
      .csrf().disable()
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests()
      .requestMatchers("/signup", "/signin").permitAll()
      .requestMatchers(HttpMethod.POST, "/products")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/products/**")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.DELETE, "/products/{id}")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/products/{id}/discounts/{discountId}")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.POST, "/products/{id}/discounts")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.GET, "/users/{userid}")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.DELETE, "/users/{userid}")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/users/{userid}/balance")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.PUT, "/users/{userid}/freeze")
      .hasRole("ADMIN")
      .requestMatchers(HttpMethod.POST, "/users/{userid}/notifications")
      .hasRole("ADMIN")
      .anyRequest().authenticated()
      .and()
      .httpBasic();
    return httpSecurity.build();
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

