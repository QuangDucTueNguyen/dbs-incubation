package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.exception.GuestErrorResponse;
import com.incubator.dbs.guestservice.exception.GuestServiceException;
import com.incubator.dbs.guestservice.model.dto.UserCredentialDto;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailServiceImp implements UserCredential {

  private final UserProfileRepository userProfileRepository;

  public UserDetailServiceImp(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  @Override
  public UserCredentialDto loadUserByUsername(String s) {
    log.info("load user by username {}.", s);
    return userProfileRepository.findOneByUsername(s)
        .map(profile -> new UserCredentialDto(profile.getUsername(), profile.getPassword().getPassword()))
        .orElseThrow(() -> new GuestServiceException(GuestErrorResponse.NOT_AUTHORIZED));
  }
}
