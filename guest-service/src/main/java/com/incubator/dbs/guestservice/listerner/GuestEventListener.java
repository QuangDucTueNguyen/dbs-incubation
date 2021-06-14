package com.incubator.dbs.guestservice.listerner;

import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.model.event.GuestAddEvent;
import com.incubator.dbs.guestservice.model.event.GuestGetOneQuery;
import com.incubator.dbs.guestservice.model.event.GuestRemoveEvent;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GuestEventListener {

  private final UserProfileRepository userProfileRepository;

  public GuestEventListener(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
  }

  @EventHandler
  public void on(GuestAddEvent guestAddEvent) {
    log.info("handle a guest even {}", guestAddEvent);
    var profile = UserProfile.builder()
        .id(guestAddEvent.getId())
        .address(guestAddEvent.getAddress())
        .name(guestAddEvent.getName())
        .phoneNumber(guestAddEvent.getPhoneNumber())
        .build();
    userProfileRepository.save(profile);
  }

  @EventHandler
  public void on(GuestRemoveEvent guestRemoveEvent){
    log.info("handle remove one guest event {}", guestRemoveEvent);
    userProfileRepository.findById(guestRemoveEvent.getId())
        .ifPresent(userProfileRepository::delete);
  }

  @QueryHandler
  public Optional<UserProfile> on(GuestGetOneQuery guestGetOneEvent){
    log.info("handle get one guest even {}", guestGetOneEvent);
    return userProfileRepository.findById(guestGetOneEvent.getId());
  }
}
