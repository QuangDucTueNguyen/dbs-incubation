package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.exception.GuestErrorResponse;
import com.incubator.dbs.guestservice.exception.GuestServiceException;
import com.incubator.dbs.guestservice.model.command.GuestAddCommand;
import com.incubator.dbs.guestservice.model.command.GuestRemoveCommand;
import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.model.event.GuestGetOneQuery;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


  private final CommandGateway commandGateway;
  private final UserProfileRepository userProfileRepository;
  private final QueryGateway queryGateway;

  public UserServiceImpl(CommandGateway commandGateway,
      UserProfileRepository userProfileRepository, QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.userProfileRepository = userProfileRepository;
    this.queryGateway = queryGateway;
  }

  @Override
  public GuestInfoResponseDto create(CreateGuestRequestDto request) {
    log.info("create request. {}", request);

    var rs = commandGateway.sendAndWait(GuestAddCommand.builder()
        .id(UUID.randomUUID())
        .address(request.getAddress())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build());

    return GuestInfoResponseDto.builder().id((UUID) rs).build();
  }

  @SneakyThrows
  @Override
  public GuestInfoResponseDto get(UUID id) {
    log.info("get user [{}]", id);
    var rs = queryGateway.query(GuestGetOneQuery.builder().id(id).build(), UserProfile.class);
    return Optional.ofNullable(rs.get(10, TimeUnit.SECONDS))
        .map(this::convertToGuestInfo)
        .orElseThrow(() -> {
          log.error("Can not find user [{}]", id);
          return new GuestServiceException(GuestErrorResponse.NOT_FOUND);
        });
  }

  @Override
  public void delete(UUID id) {
    log.info("delete user [{}]", id);
    commandGateway.send(GuestRemoveCommand.builder()
        .id(id)
        .build());
  }

  private GuestInfoResponseDto convertToGuestInfo(UserProfile userProfile) {
    return GuestInfoResponseDto.builder()
        .id(userProfile.getId())
        .creditCard(userProfile.getCreditCard())
        .address(userProfile.getAddress())
        .phoneNumber(userProfile.getPhoneNumber())
        .name(userProfile.getName())
        .build();
  }
}
