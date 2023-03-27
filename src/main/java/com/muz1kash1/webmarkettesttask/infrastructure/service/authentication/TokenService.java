package com.muz1kash1.webmarkettesttask.infrastructure.service.authentication;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TokenService {
  private final JwtEncoder encoder;

  public TokenService(final JwtEncoder encoder) {
    this.encoder = encoder;
  }

  public String generateToken(Authentication authentication){
    Instant now = Instant.now();
    log.info(authentication.toString());
    log.info(authentication.getPrincipal().toString());
    String authorities = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
    log.info(authorities);
    JwtClaimsSet claimsSet = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt((now))
      .expiresAt(now.plus(1, ChronoUnit.HOURS))
      .subject(authentication.getName())
      .claim("scope",authorities)
      .build();
    log.info(claimsSet.getClaims().toString());
    return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

  }
}
