package com.incubator.dbs.hotelservice.controller;

import com.incubator.dbs.hotelservice.model.dto.CreateRoomRequest;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomTypeResponse;
import com.incubator.dbs.hotelservice.model.dto.UpdateRoomRequest;
import com.incubator.dbs.hotelservice.service.RoomService;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomsControllerTest {

  private final RoomService roomService;
  private final RoomsController roomController;
  private final Integer ROOM_ID_1 = new Random().nextInt();
  private final String ROOM_NAME_1 = "ROOM_NAME_1";
  private final Integer HOTEL_ID = Integer.MAX_VALUE;
  private final String HOTEL_NAME = "HOTEL_NAME";
  private final String DESCRIPTION = RandomStringUtils.randomAlphabetic(1000);
  private final Integer ROOM_TYPE_ID_1 = new Random().nextInt();
  private final Integer ROOM_TYPE_ID_2 = new Random().nextInt();
  private final String ROOM_TYPE_NAME_1 = "ROOM_TYPE_NAME_1";
  private final String ROOM_TYPE_NAME_2 = "ROOM_TYPE_NAME_2";
  private final Integer NUMBER_AVAILABLE = 5;
  private final Integer NUMBER_PERSON_PER_ROOM = new Random().nextInt(10);
  private final Instant from = Instant.now();
  private final Instant to = Instant.now().plusMillis(5 * 24 * 60 * 60 * 100);

  public RoomsControllerTest() {
    roomService = Mockito.mock(RoomService.class);
    roomController = new RoomsController(roomService);
  }

  @Test
  void getAllRoomType_shouldWork() {
    var roomType1 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_1)
        .type(ROOM_TYPE_NAME_1)
        .numberPersonPerRoom(NUMBER_PERSON_PER_ROOM)
        .build();
    var roomType2 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_2)
        .type(ROOM_TYPE_NAME_2)
        .numberPersonPerRoom(NUMBER_PERSON_PER_ROOM)
        .build();
    var expected = List.of(roomType1, roomType2);
    Mockito.when(roomService.getAllRoomType()).thenReturn(expected);
    var result = roomController.getAllRoomType();
    Assertions.assertEquals(expected.size(), result.size());
    Assertions.assertEquals(expected.get(0).getId(), result.get(0).getId());
    Assertions.assertEquals(expected.get(0).getType(), result.get(0).getType());
    Assertions.assertEquals(expected.get(0).getNumberPersonPerRoom(), result.get(0).getNumberPersonPerRoom());
    Assertions.assertEquals(expected.get(1).getId(), result.get(1).getId());
    Assertions.assertEquals(expected.get(1).getType(), result.get(1).getType());
    Assertions.assertEquals(expected.get(1).getNumberPersonPerRoom(), result.get(1).getNumberPersonPerRoom());
  }

  @Test
  void createRoom_shouldWork() {
    var request = CreateRoomRequest.builder()
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .build();
    var expected = RoomInfoResponse.builder()
        .id(ROOM_ID_1)
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .type(ROOM_TYPE_NAME_1)
        .build();
    Mockito.when(roomService.createRoom(request)).thenReturn(expected);
    var result = roomController.createRoom(request);
    Assertions.assertNotNull(expected.getId());
    Assertions.assertEquals(expected.getName(), result.getName());
    Assertions.assertEquals(expected.getDescription(), result.getDescription());
  }

  @Test
  void updateRoom_shouldWork() {
    var request = UpdateRoomRequest.builder()
        .description(DESCRIPTION)
        .build();
    var expected = RoomInfoResponse.builder()
        .id(ROOM_ID_1)
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .type(ROOM_TYPE_NAME_1)
        .build();
    Mockito.when(roomService.updateRoom(ROOM_ID_1, request)).thenReturn(expected);
    var result = roomController.updateRoom(ROOM_ID_1, request);
    Assertions.assertNotNull(expected.getId());
    Assertions.assertEquals(expected.getName(), result.getName());
    Assertions.assertEquals(expected.getDescription(), result.getDescription());
  }

  @Test
  void getAllRoomByTypeAndArrangeTime_shouldWork() {
    var roomType1 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_1)
        .type(ROOM_TYPE_NAME_1)
        .numberPersonPerRoom(NUMBER_PERSON_PER_ROOM)
        .build();
    var roomType2 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_2)
        .type(ROOM_TYPE_NAME_2)
        .numberPersonPerRoom(NUMBER_PERSON_PER_ROOM)
        .build();

    var expected = List.of(roomType1, roomType2);
    Mockito.when(roomService.getAllRoomInArrangeTime(this.from, this.to)).thenReturn(expected);
    var result = roomController.getAllRoomInArrangeTime(this.from, this.to);
    Assertions.assertEquals(expected.size(), result.size());
    Assertions.assertEquals(expected.get(0).getType(), result.get(0).getType());
    Assertions.assertEquals(expected.get(0).getNumberPersonPerRoom(), result.get(0).getNumberPersonPerRoom());
    Assertions.assertEquals(expected.get(1).getType(), result.get(1).getType());
    Assertions.assertEquals(expected.get(1).getNumberPersonPerRoom(), result.get(1).getNumberPersonPerRoom());
  }
}
