package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.GuestInfoResponse;
import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.repository.PasswordRepository;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import com.incubator.dbs.guestservice.utility.JWTUtility;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

@ExtendWith({MockitoExtension.class})
class UserServiceTest {

  private final String USERNAME = "USERNAME";
  private final String PASSWORD = "PASSWORD";
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "+8412345678";
  private final String NAME = "NAME";
  private final String SECRET_KEY = "SECRET_KEY";
  private final UUID USER_ID = UUID.randomUUID();
  private final UserService userService;
  private UserDetailsService userDetailsService;
  private UserProfileRepository userProfileRepository;
  private PasswordRepository passwordRepository;
  private JWTUtility jwtUtility;

  public UserServiceTest() {
    userDetailsService = Mockito.mock(UserDetailsService.class);
    userProfileRepository = Mockito.mock(UserProfileRepository.class);
    passwordRepository = Mockito.mock(PasswordRepository.class);
    jwtUtility = new JWTUtility();
    ReflectionTestUtils.setField(jwtUtility, "secretKey", SECRET_KEY);
    userService = new UserServiceImpl(jwtUtility, userDetailsService, userProfileRepository,
        passwordRepository);
  }

  @Test
  void signUp_shouldWork() {
    var signUpRequest = SignUpRequestDto.builder().name(NAME).address(ADDRESS).phoneNumber(PHONE_NUMBER)
        .username(USERNAME).build();
    Mockito.when(userProfileRepository.findOneByUsername(USERNAME)).thenReturn(Optional.empty());
    var result = userService.signUp(signUpRequest);
    Assertions.assertFalse(StringUtils.isEmpty(result));
  }

  @Test
  void login_shouldWork() {
    var loginRequest = LoginRequestDto.builder().username(USERNAME).password(PASSWORD).build();
    UserDetails userDetails = new User(USERNAME, PASSWORD, Collections.emptyList());
    Mockito.when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
    var result = userService.login(loginRequest);
    Assertions.assertNotNull(result.getAccessToken());
  }

  @Test
  void delete_shouldWork() {
    userService.delete(USER_ID.toString());
    Mockito.verify(userProfileRepository, Mockito.times(1)).deleteById(USER_ID.toString());
  }

  @Test
  void get_shouldWork() {
    var guestEntity = UserProfile.builder()
        .id(USER_ID)
        .username(USERNAME)
        .phoneNumber(PHONE_NUMBER)
        .build();
    var expected = GuestInfoResponse.builder()
        .username(USERNAME)
        .id(USER_ID)
        .phoneNumber(PHONE_NUMBER)
        .build();
    Mockito.when(userProfileRepository.findById(USER_ID.toString())).thenReturn(Optional.of(guestEntity));
    var result = userService.get(USER_ID.toString());
    Assertions.assertEquals(expected, result);
  }
}
