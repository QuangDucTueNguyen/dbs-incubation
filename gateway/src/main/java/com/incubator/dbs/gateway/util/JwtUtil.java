package com.incubator.dbs.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secretKey;

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return this.getAllClaimsFromToken(token).getExpiration().after(new Date());
  }

  public Boolean isInvalid(String token) {
    return this.isTokenExpired(token);
  }

}
