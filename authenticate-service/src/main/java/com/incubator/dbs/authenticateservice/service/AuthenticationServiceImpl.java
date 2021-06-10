package com.incubator.dbs.authenticateservice.service;

import com.incubator.dbs.authenticateservice.exception.AuthenticationErrorResponse;
import com.incubator.dbs.authenticateservice.exception.AuthenticationServiceException;
import com.incubator.dbs.authenticateservice.model.constant.CreateGuestStatus;
import com.incubator.dbs.authenticateservice.model.constant.RoleName;
import com.incubator.dbs.authenticateservice.model.dto.CreateUserInfoDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginResponseDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.SignupResponseDto;
import com.incubator.dbs.authenticateservice.model.entity.CredentialEntity;
import com.incubator.dbs.authenticateservice.model.entity.UserRoleEntity;
import com.incubator.dbs.authenticateservice.model.template.CreateGuest;
import com.incubator.dbs.authenticateservice.repository.RoleRepository;
import com.incubator.dbs.authenticateservice.repository.UserCredentialRepository;
import com.incubator.dbs.authenticateservice.repository.UserRoleRepository;
import com.incubator.dbs.authenticateservice.service.connector.GuestServiceConnector;
import com.incubator.dbs.authenticateservice.utility.JWTUtility;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JWTUtility jwtUtility;
  private final UserCredentialRepository userCredentialRepository;
  private static final int RANDOM_PASSWORD_LENGTH = 6;
  private final GuestServiceConnector guestServiceConnector;
  private final RoleRepository roleRepository;
  private final UserRoleRepository userRoleRepository;
  @Autowired
  private ProducerService producerService;

  public AuthenticationServiceImpl(
      JWTUtility jwtUtility,
      UserCredentialRepository userCredentialRepository,
      GuestServiceConnector guestServiceConnector,
      RoleRepository roleRepository,
      UserRoleRepository userRoleRepository) {
    this.jwtUtility = jwtUtility;
    this.userCredentialRepository = userCredentialRepository;
    this.guestServiceConnector = guestServiceConnector;
    this.roleRepository = roleRepository;
    this.userRoleRepository = userRoleRepository;
  }

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    log.info("User [{}] request log in.", request.getUsername());
    final var userCredential =
        userCredentialRepository.findOneByUsernameAndPassword(request.getUsername(), request.getPassword())
            .orElseThrow(() -> new AuthenticationServiceException(AuthenticationErrorResponse.NOT_AUTHORIZED));
    final String token = jwtUtility.generateToken(userCredential);
    return LoginResponseDto.builder().accessToken(token).build();
  }

  @Override
  public SignupResponseDto signUp(SignUpRequestDto request) {
    log.info("Sign up request. {}", request);
    checkUserIsExist(request.getUsername());
    request.setRole(Optional.ofNullable(request.getRole()).orElse(RoleName.USER));

    if (RoleName.ROOT.equals(request.getRole())) {
      log.error("create user with invalid role: {}", RoleName.ROOT.getValue());
      throw new AuthenticationServiceException(AuthenticationErrorResponse.INVALID_ROLE);
    }
    // Password need to be encrypt or encode before saving to db
    // We will do it in future if have time.
    var randomPass = RandomStringUtils.randomAlphabetic(RANDOM_PASSWORD_LENGTH);

    var createUserInfoRequest = CreateUserInfoDto.builder()
        .address(request.getAddress())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build();
    var userInfo = guestServiceConnector.create(createUserInfoRequest);
    log.info("Create user info. {}", userInfo);

    try {
      var roles = roleRepository.findByName(request.getRole()).orElseThrow(() -> {
        log.error("create user with invalid role: {}", request.getRole().getValue());
        return new AuthenticationServiceException(AuthenticationErrorResponse.INVALID_ROLE);
      });
      var credentialEntity = CredentialEntity.builder()
          .guestId(userInfo.getId())
          .username(request.getUsername())
          .password(randomPass)
          .build();
      var userRoles = UserRoleEntity.builder()
          .role(roles)
          .userCredential(userCredentialRepository.save(credentialEntity))
          .build();
      userRoleRepository.save(userRoles);
      return SignupResponseDto.builder().defaultPassword(randomPass).build();
    } catch (RuntimeException ex) {
      log.error("Sign up error", ex);
      producerService.sendMessage(CreateGuest.builder()
          .id(userInfo.getId())
          .name(request.getName())
          .status(CreateGuestStatus.FAILED.toString())
          .build());
      throw ex;
    }

  }

  private void checkUserIsExist(String username) {
    userCredentialRepository.findOneByUsername(username)
        .ifPresent(userProfile -> {
          throw new AuthenticationServiceException(AuthenticationErrorResponse.USER_EXIST);
        });
  }
}
