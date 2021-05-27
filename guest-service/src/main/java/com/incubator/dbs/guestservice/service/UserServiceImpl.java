package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.exception.GuestErrorResponse;
import com.incubator.dbs.guestservice.exception.GuestServiceException;
import com.incubator.dbs.guestservice.model.dto.GuestInfoResponse;
import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.model.dto.SignupResponseDto;
import com.incubator.dbs.guestservice.model.entity.UserPassword;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.repository.PasswordRepository;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import com.incubator.dbs.guestservice.utility.JWTUtility;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


  private final JWTUtility jwtUtility;
  private final UserDetailsService userDetailsService;
  private final UserProfileRepository userProfileRepository;
  private final PasswordRepository passwordRepository;
  private static final int RANDOM_PASSWORD_LENGTH = 6;

  public UserServiceImpl(JWTUtility jwtUtility,
      UserDetailsService userDetailsService,
      UserProfileRepository userProfileRepository,
      PasswordRepository passwordRepository) {
    this.jwtUtility = jwtUtility;
    this.userDetailsService = userDetailsService;
    this.userProfileRepository = userProfileRepository;
    this.passwordRepository = passwordRepository;
  }

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    log.info("User [{}] request log in.", request);
    final var userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    if (!Objects.equals(request.getPassword(), userDetails.getPassword())) {
      throw new GuestServiceException(GuestErrorResponse.NOT_AUTHORIZED);
    }
    final String token = jwtUtility.generateToken(userDetails);
    return LoginResponseDto.builder().accessToken(token).build();
  }

  @Override
  public SignupResponseDto signUp(SignUpRequestDto request) {
    log.info("Sign up request. {}", request);
    checkUserIsExist(request.getUsername());

    // Password need to be encrypt or encode before saving to db
    // We will do it in future if have time.
    var randomPass = RandomStringUtils.randomAlphabetic(RANDOM_PASSWORD_LENGTH);

    var profile = UserProfile.builder()
        .address(request.getAddress())
        .username(request.getUsername())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build();

    passwordRepository.save(UserPassword.builder().userProfile(profile).password(randomPass).build());

    return SignupResponseDto.builder().defaultPassword(randomPass).build();
  }

  private void checkUserIsExist(String username){
    userProfileRepository.findOneByUsername(username)
        .ifPresent(userProfile -> {
          throw new GuestServiceException(GuestErrorResponse.USER_EXISTING);
        });
  }

  @Override
  public GuestInfoResponse get(String id) {
    log.info("get user [{}]", id);
    return userProfileRepository.findById(id)
        .map(this::convertToGuestInfo)
        .orElseThrow(() -> {
          log.error("Can not find user [{}]", id);
          return new GuestServiceException(GuestErrorResponse.NOT_AUTHORIZED);
        });
  }

  @Override
  public void delete(String id) {
    log.info("delete user [{}]", id);
    userProfileRepository.deleteById(id);
  }

  private GuestInfoResponse convertToGuestInfo(UserProfile userProfile){
    return GuestInfoResponse.builder()
        .id(userProfile.getId())
        .creditCard(userProfile.getCreditCard())
        .address(userProfile.getAddress())
        .username(userProfile.getUsername())
        .phoneNumber(userProfile.getPhoneNumber())
        .name(userProfile.getName())
        .build();
  }
}
