package com.incubator.dbs.guestservice.controller;

import com.incubator.dbs.guestservice.model.dto.GuestInfoResponse;
import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.model.dto.SignupResponseDto;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.service.UserService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  private UserOperation userOperation;
  private UserService userService;
  private final String USERNAME = "USERNAME";
  private final String PASSWORD = "PASSWORD";
  private final String DEFAULT_PASSWORD = "PASSWORD";
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "+8412345678";
  private final String NAME = "NAME";
  private final String ACCESS_TOKEN = "ACCESS_TOKEN";
  private final UUID USER_ID = UUID.randomUUID();

  public UserControllerTest() {
    userService = Mockito.mock(UserService.class);
    userOperation = new UserController(userService);
  }

  @Test
  public void signUp_shouldWork() {
    var signUpRequest = SignUpRequestDto.builder().name(NAME).address(ADDRESS).phoneNumber(PHONE_NUMBER)
        .username(USERNAME).build();
    var expected = SignupResponseDto.builder().defaultPassword(DEFAULT_PASSWORD).build();
    Mockito.when(userService.signUp(signUpRequest)).thenReturn(expected);
    var result = userOperation.signUp(signUpRequest);
    Assertions.assertEquals(expected.getDefaultPassword(), result.getDefaultPassword());
  }

  @Test
  public void login_shouldWork() {
    var loginRequest = LoginRequestDto.builder().username(USERNAME).password(PASSWORD).build();
    var expected = LoginResponseDto.builder().accessToken(ACCESS_TOKEN).build();
    Mockito.when(userService.login(loginRequest)).thenReturn(expected);
    var result = userOperation.login(loginRequest);
    Assertions.assertEquals(expected.getAccessToken(), result.getAccessToken());
  }
  @Test
  void delete_shouldWork() {
    userOperation.delete(USER_ID.toString());
    Mockito.verify(userService, Mockito.times(1)).delete(USER_ID.toString());

  }

  @Test
  void get_shouldWork() {
    var expected = GuestInfoResponse.builder()
        .username(USERNAME)
        .id(USER_ID)
        .phoneNumber(PHONE_NUMBER)
        .build();
    Mockito.when(userService.get(USER_ID.toString())).thenReturn(expected);
    var result = userService.get(USER_ID.toString());
    Assertions.assertEquals(expected, result);
  }
}
