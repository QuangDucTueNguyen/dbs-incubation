package com.incubator.dbs.authenticateservice.utility;

import com.incubator.dbs.authenticateservice.model.entity.CredentialEntity;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class JWTUtilityTest {

  private JWTUtility jwtUtility = new JWTUtility();
  private final String SECRET_KEY = "SECRET_KEY";
  private final String USERNAME = "USERNAME";
  private final UUID USER_ID = UUID.randomUUID();

  @BeforeEach
  public void setup(){
    ReflectionTestUtils.setField(jwtUtility, "secretKey", SECRET_KEY);
  }
  @Test
  public void generateToken_shouldWork(){
    var entity = CredentialEntity.builder()
        .username(USERNAME)
        .guestId(USER_ID)
        .build();
    var result = jwtUtility.generateToken(entity);
    Assertions.assertNotNull(result);
  }

}
