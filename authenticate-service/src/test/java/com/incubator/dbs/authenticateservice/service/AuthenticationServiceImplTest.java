package com.incubator.dbs.authenticateservice.service;

import com.incubator.dbs.authenticateservice.model.constant.RoleName;
import com.incubator.dbs.authenticateservice.model.dto.CreateUserInfoDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.UserInfoResponseDto;
import com.incubator.dbs.authenticateservice.model.entity.CredentialEntity;
import com.incubator.dbs.authenticateservice.model.entity.RoleEntity;
import com.incubator.dbs.authenticateservice.model.entity.UserRoleEntity;
import com.incubator.dbs.authenticateservice.repository.RoleRepository;
import com.incubator.dbs.authenticateservice.repository.UserCredentialRepository;
import com.incubator.dbs.authenticateservice.repository.UserRoleRepository;
import com.incubator.dbs.authenticateservice.service.connector.GuestServiceConnector;
import com.incubator.dbs.authenticateservice.utility.JWTUtility;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

  private final String USERNAME = "USERNAME";
  private final String PASSWORD = "PASSWORD";
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "+8412345678";
  private final String NAME = "NAME";
  private final String SECRET_KEY = "SECRET_KEY";
  private final AuthenticationService authenticationService;
  private UserCredentialRepository userCredentialRepository;
  private JWTUtility jwtUtility;
  private final UUID USER_ID = UUID.randomUUID();
  private final GuestServiceConnector guestServiceConnector;
  private final RoleRepository roleRepository;
  private final UserRoleRepository userRoleRepository;

  public AuthenticationServiceImplTest() {
    userCredentialRepository = Mockito.mock(UserCredentialRepository.class);
    guestServiceConnector = Mockito.mock(GuestServiceConnector.class);
    roleRepository = Mockito.mock(RoleRepository.class);
    userRoleRepository = Mockito.mock(UserRoleRepository.class);
    jwtUtility = new JWTUtility();
    ReflectionTestUtils.setField(jwtUtility, "secretKey", SECRET_KEY);
    authenticationService = new AuthenticationServiceImpl(jwtUtility, userCredentialRepository, guestServiceConnector,
        roleRepository, userRoleRepository);
  }

  @Test
  void signUp_shouldWork() {
    var signUpRequest = SignUpRequestDto.builder().name(NAME).address(ADDRESS).phoneNumber(PHONE_NUMBER)
        .username(USERNAME).build();
    var createUserInfoRequest = CreateUserInfoDto.builder()
        .address(signUpRequest.getAddress())
        .name(signUpRequest.getName())
        .phoneNumber(signUpRequest.getPhoneNumber())
        .build();
    var userInfoResponseDto = UserInfoResponseDto.builder()
        .address(signUpRequest.getAddress())
        .name(signUpRequest.getName())
        .phoneNumber(signUpRequest.getPhoneNumber())
        .id(USER_ID)
        .build();
    var credentialEntity = CredentialEntity.builder()
        .guestId(UUID.randomUUID())
        .username(signUpRequest.getUsername())
        .build();
    var roleEntity = RoleEntity.builder()
        .name(RoleName.USER)
        .description(RoleName.USER.getValue())
        .build();
    var userRoleEntity = UserRoleEntity.builder()
        .role(roleEntity)
        .build();
    Mockito.when(guestServiceConnector.create(createUserInfoRequest)).thenReturn(userInfoResponseDto);
    Mockito.when(userCredentialRepository.findOneByUsername(USERNAME)).thenReturn(Optional.empty());
    Mockito.when(userCredentialRepository.save(Mockito.any(CredentialEntity.class))).thenReturn(credentialEntity);
    Mockito.when(roleRepository.findByName(RoleName.USER)).thenReturn(Optional.of(roleEntity));
    Mockito.when(userRoleRepository.save(userRoleEntity)).thenReturn(userRoleEntity);
    var result = authenticationService.signUp(signUpRequest);
    Assertions.assertFalse(StringUtils.isEmpty(result));
  }

  @Test
  void login_shouldWork() {
    var loginRequest = LoginRequestDto.builder().username(USERNAME).password(PASSWORD).build();
    var roleEntity = RoleEntity.builder()
        .name(RoleName.USER)
        .build();
    var userRoleEntity = UserRoleEntity.builder()
        .role(roleEntity)
        .build();
    var entity = CredentialEntity.builder()
        .username(USERNAME)
        .guestId(USER_ID)
        .userRoleEntities(List.of(userRoleEntity))
        .build();
    Mockito.when(
        userCredentialRepository.findOneByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword()))
        .thenReturn(Optional.of(entity));
    var result = authenticationService.login(loginRequest);
    Assertions.assertNotNull(result.getAccessToken());
  }
}
