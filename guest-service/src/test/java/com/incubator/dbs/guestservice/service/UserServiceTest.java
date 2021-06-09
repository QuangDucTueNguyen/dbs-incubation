package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.handler.command.GuestCommandHandler;
import com.incubator.dbs.guestservice.model.command.GuestAddCommand;
import com.incubator.dbs.guestservice.model.command.GuestRemoveCommand;
import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.entity.UserProfile;
import com.incubator.dbs.guestservice.model.event.GuestGetOneQuery;
import com.incubator.dbs.guestservice.repository.UserProfileRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "+8412345678";
  private final String NAME = "NAME";
  private final UUID USER_ID = UUID.randomUUID();
  private final UserService userService;
  private CommandGateway commandGateway;
  private QueryGateway queryGateway;

  public UserServiceTest() {

    commandGateway = Mockito.mock(CommandGateway.class);
    queryGateway = Mockito.mock(QueryGateway.class);
    userService = new UserServiceImpl(commandGateway, queryGateway);
  }

  @Test
  void create_shouldWork() {
    var request = CreateGuestRequestDto.builder()
        .name(NAME)
        .address(ADDRESS)
        .phoneNumber(PHONE_NUMBER)
        .build();
    Mockito.when(commandGateway.sendAndWait(Mockito.any(GuestAddCommand.class))).thenReturn(USER_ID);
    var result = userService.create(request);
    Assertions.assertEquals(USER_ID, result.getId());
  }

  @Test
  void delete_shouldWork() {
    userService.delete(USER_ID);
    Mockito.verify(commandGateway, Mockito.times(1)).send(Mockito.any(GuestRemoveCommand.class));
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
    var cf = new CompletableFuture<UserProfile>();
    cf.complete(guestEntity);
    Mockito.when(queryGateway.query(GuestGetOneQuery.builder().id(USER_ID).build(), UserProfile.class)).thenReturn(cf);
    var result = userService.get(USER_ID);
    Assertions.assertEquals(expected, result);
  }
}
