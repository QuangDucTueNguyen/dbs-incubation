package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.exception.GuestErrorResponse;
import com.incubator.dbs.guestservice.exception.GuestServiceException;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailServiceImp implements UserDetailsService {

  private final UserProfileRepository userProfileRepository;

  public UserDetailServiceImp(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) {
    log.info("load user by username {}.", s);
    return userProfileRepository.findOneByUsername(s)
        .map(profile -> new User(profile.getUsername(), profile.getPassword().getPassword(),
            Collections.emptyList())).orElseThrow(() -> new GuestServiceException(GuestErrorResponse.NOT_AUTHORIZED));
  }
}
