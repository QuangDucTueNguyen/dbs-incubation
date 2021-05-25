package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.exception.GuestServiceException;
import com.incubator.dbs.guestservice.model.entity.UserPassword;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith({MockitoExtension.class})
public class UserDetailServiceImpTest {

  private final String USERNAME = "USERNAME";
  private final String PASSWORD = "PASSWORD";
  private final UserDetailsService userDetailsService;
  private final UserProfileRepository userProfileRepository;

  public UserDetailServiceImpTest() {
    userProfileRepository = Mockito.mock(UserProfileRepository.class);
    userDetailsService = new UserDetailServiceImp(userProfileRepository);
  }

  @Test
  public void loadUserByUsername_shouldWork(){
    var expected = new User(USERNAME, PASSWORD, Collections.emptyList());
    var userProfile = UserProfile.builder()
        .username(USERNAME)
        .password(UserPassword.builder().password(USERNAME).build())
        .build();
    Mockito.when(userProfileRepository.findOneByUsername(USERNAME)).thenReturn(Optional.of(userProfile));
    var result = userDetailsService.loadUserByUsername(USERNAME);
    Assertions.assertEquals(expected.getUsername(), result.getUsername());
  }

  @Test
  public void loadUserByUsername_shouldThrow_GuestServiceException(){
    Mockito.when(userProfileRepository.findOneByUsername(USERNAME)).thenReturn(Optional.empty());
    Assertions.assertThrows(GuestServiceException.class, () -> userDetailsService.loadUserByUsername(USERNAME));
  }
}
