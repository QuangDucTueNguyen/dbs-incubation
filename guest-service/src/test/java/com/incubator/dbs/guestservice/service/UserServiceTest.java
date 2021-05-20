package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
public class UserServiceTest {

  private final String USERNAME = "USERNAME";
  private final String PASSWORD = "PASSWORD";
  private final String DEFAULT_PASSWORD = "DEFAULT_PASSWORD";
  private final String ACCESS_TOKEN = "ACCESS_TOKEN";
  private final Long EXPIRE_IN = 1_000_000L;
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "+8412345678";
  private final String NAME = "NAME";
  private final UserService userService;

  @Mock
  private UserDetailsService userDetailsService;

  @Mock
  private AuthenticationManager authenticationManager;

  public UserServiceTest() {
    userService = new UserServiceImpl();
  }

  @BeforeEach
  public void setup(){

  }

  @Test
  public void signUp_shouldWork() {
    var signUpRequest = SignUpRequestDto.builder().name(NAME).address(ADDRESS).phoneNumber(PHONE_NUMBER)
        .username(USERNAME).build();
    var result = userService.signUp(signUpRequest);
    Assertions.assertEquals(DEFAULT_PASSWORD, result);
  }

  @Test
  public void login_shouldWork() {
    var loginRequest = LoginRequestDto.builder().username(USERNAME).password(PASSWORD).build();
    var expected = LoginResponseDto.builder().accessToken(ACCESS_TOKEN).build();
    var result = userService.login(loginRequest);
    var userPassAuthenticateToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
        loginRequest.getPassword());
    Assertions.assertDoesNotThrow(() -> authenticationManager.authenticate(userPassAuthenticateToken));

    var userDetails = new User(USERNAME, PASSWORD, Collections.emptyList());
    Mockito.when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);

    Assertions.assertEquals(expected.getAccessToken(), result.getAccessToken());
  }

}
