package com.incubator.dbs.authenticateservice.service;

import com.incubator.dbs.authenticateservice.exception.AuthenticationErrorResponse;
import com.incubator.dbs.authenticateservice.exception.AuthenticationServiceException;
import com.incubator.dbs.authenticateservice.model.dto.CreateUserInfoDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginResponseDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.SignupResponseDto;
import com.incubator.dbs.authenticateservice.model.entity.CredentialEntity;
import com.incubator.dbs.authenticateservice.repository.UserCredentialRepository;
import com.incubator.dbs.authenticateservice.service.connector.GuestServiceConnector;
import com.incubator.dbs.authenticateservice.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JWTUtility jwtUtility;
  private final UserCredentialRepository userCredentialRepository;
  private static final int RANDOM_PASSWORD_LENGTH = 6;
  private final GuestServiceConnector guestServiceConnector;

  public AuthenticationServiceImpl(
      JWTUtility jwtUtility,
      UserCredentialRepository userCredentialRepository,
      GuestServiceConnector guestServiceConnector) {
    this.jwtUtility = jwtUtility;
    this.userCredentialRepository = userCredentialRepository;
    this.guestServiceConnector = guestServiceConnector;
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

    var credentialEntity = CredentialEntity.builder()
        .guestId(userInfo.getId())
        .username(request.getUsername())
        .password(randomPass)
        .build();

    userCredentialRepository.save(credentialEntity);
    return SignupResponseDto.builder().defaultPassword(randomPass).build();
  }

  private void checkUserIsExist(String username){
    userCredentialRepository.findOneByUsername(username)
        .ifPresent(userProfile -> {
          throw new AuthenticationServiceException(AuthenticationErrorResponse.USER_EXIST);
        });
  }
}
