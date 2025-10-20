package com.example.letsplay.auth;

import com.example.letsplay.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/** JWT issue/parse utilities (HS256). */
@Service
public class JwtService {

  @Value("${security.jwt.secret}")
  private String jwtSecret;

  @Value("${security.jwt.expiration-ms:86400000}") // default 24h
  private long jwtExpirationMs;

  public String generateToken(User user) {
    return Jwts.builder()
        .subject(user.getUsername())               // email
        .claim("uid", user.getId())
        .claim("role", user.getRole().name())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(getSignKey())
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> resolver) {
    Claims claims = Jwts.parser()
        .verifyWith(getSignKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return resolver.apply(claims);
  }

  /** Soft validation used by the filter before authenticating the user. */
  public boolean isTokenValid(String token, User user) {
    try {
      String subject = extractUsername(token);
      return subject != null && subject.equals(user.getUsername());
    } catch (Exception e) {
      return false;
    }
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
