package com.incubator.dbs.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.sql.Date;
import java.time.Instant;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret_key;


  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return this.getAllClaimsFromToken(token).getExpiration().before(Date.from(Instant.now()));
  }

  public Boolean isInvalid(String token) {
    return this.isTokenExpired(token);
  }

}
