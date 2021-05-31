package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
  private UserProfileRepository userProfileRepository;

  public UserServiceTest() {
    userProfileRepository = Mockito.mock(UserProfileRepository.class);

    userService = new UserServiceImpl( userProfileRepository);
  }

  @Test
  void create_shouldWork() {
    var request = CreateGuestRequestDto.builder()
        .name(NAME)
        .address(ADDRESS)
        .phoneNumber(PHONE_NUMBER)
        .build();

    var profile = UserProfile.builder()
        .address(request.getAddress())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build();

    var expected = GuestInfoResponseDto.builder()
        .address(profile.getAddress())
        .name(profile.getName())
        .phoneNumber(profile.getPhoneNumber())
        .build();
    Mockito.when(userProfileRepository.save(profile)).thenReturn(profile);
    var result = userService.create(request);
    Assertions.assertEquals(expected, result);
  }

  @Test
  void delete_shouldWork() {
    userService.delete(USER_ID);
    Mockito.verify(userProfileRepository, Mockito.times(1)).deleteById(USER_ID);
  }

  @Test
  void get_shouldWork() {
    var guestEntity = UserProfile.builder()
        .id(USER_ID)
        .phoneNumber(PHONE_NUMBER)
        .build();
    var expected = GuestInfoResponseDto.builder()
        .id(USER_ID)
        .phoneNumber(PHONE_NUMBER)
        .build();
    Mockito.when(userProfileRepository.findById(USER_ID)).thenReturn(Optional.of(guestEntity));
    var result = userService.get(USER_ID);
    Assertions.assertEquals(expected, result);
  }
}
