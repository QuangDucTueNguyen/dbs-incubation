package com.incubator.dbs.authenticateservice.utility;

import com.incubator.dbs.authenticateservice.model.entity.CredentialEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtility implements Serializable {

  private static final long serialVersionUID = 234234523523L;
  private static final String PREFERRED_USERNAME = "preferred_username";
  private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60L;

  @Value("${jwt.secret}")
  private String secretKey;

  public String generateToken(CredentialEntity credentialEntity) {
    Map<String, Object> claims = new HashMap<>();
    claims.putIfAbsent(PREFERRED_USERNAME, credentialEntity.getUsername());
    return doGenerateToken(claims, credentialEntity.getGuestId().toString());
  }


  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }
}
