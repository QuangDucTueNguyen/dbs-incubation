package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.exception.GuestErrorResponse;
import com.incubator.dbs.guestservice.exception.GuestServiceException;
import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


  private final UserProfileRepository userProfileRepository;

  public UserServiceImpl(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  @Override
  public GuestInfoResponseDto create(CreateGuestRequestDto request) {
    log.info("create request. {}", request);

    var profile = UserProfile.builder()
        .address(request.getAddress())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build();

    return convertToGuestInfo(userProfileRepository.save(profile));
  }

  @Override
  public GuestInfoResponseDto get(String id) {
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

  private GuestInfoResponseDto convertToGuestInfo(UserProfile userProfile){
    return GuestInfoResponseDto.builder()
        .id(userProfile.getId())
        .creditCard(userProfile.getCreditCard())
        .address(userProfile.getAddress())
        .phoneNumber(userProfile.getPhoneNumber())
        .name(userProfile.getName())
        .build();
  }
}
