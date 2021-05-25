package com.incubator.dbs.hotelservice.service;

import com.incubator.dbs.hotelservice.exception.HotelServiceException;
import com.incubator.dbs.hotelservice.model.constant.RoomStatus;
import com.incubator.dbs.hotelservice.model.dto.CreateRoomRequest;
import com.incubator.dbs.hotelservice.model.dto.ReservationResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomTypeResponse;
import com.incubator.dbs.hotelservice.model.dto.UpdateRoomRequest;
import com.incubator.dbs.hotelservice.model.entity.Hotel;
import com.incubator.dbs.hotelservice.model.entity.Room;
import com.incubator.dbs.hotelservice.model.entity.RoomType;
import com.incubator.dbs.hotelservice.repository.HotelRepository;
import com.incubator.dbs.hotelservice.repository.RoomRepository;
import com.incubator.dbs.hotelservice.repository.RoomTypeRepository;
import com.incubator.dbs.hotelservice.service.connector.ReservationConnector;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

  private final RoomService roomService;
  private final RoomRepository roomRepository;
  private final RoomTypeRepository roomTypeRepository;
  private final ReservationConnector reservationConnector;
  private final HotelRepository hotelRepository;
  private final Integer ROOM_ID_1 = new Random().nextInt();
  private final String ROOM_NAME_1 = "ROOM_NAME_1";
  private final Integer ROOM_ID_2 = new Random().nextInt();
  private final String ROOM_NAME_2 = "ROOM_NAME_1";
  private final Integer HOTEL_ID = Integer.MAX_VALUE;
  private final String HOTEL_NAME = "HOTEL_NAME";
  private final String HOTEL_ADDRESS = "HOTEL_ADDRESS";
  private final String DESCRIPTION = RandomStringUtils.randomAlphabetic(1000);
  private final Integer ROOM_TYPE_ID_1 = new Random().nextInt();
  private final Integer ROOM_TYPE_ID_2 = new Random().nextInt();
  private final String ROOM_TYPE_NAME_1 = "ROOM_TYPE_NAME_1";
  private final Integer NUMBER_AVAILABLE = 5;
  private final Integer NUMBER_PERSON_PER_ROOM = new Random().nextInt(10);
  private final Instant from = Instant.now();
  private final Instant to = Instant.now().plusMillis(5 * 24 * 60 * 60 * 100);

  public RoomServiceTest() {
    roomRepository = Mockito.mock(RoomRepository.class);
    roomTypeRepository = Mockito.mock(RoomTypeRepository.class);
    hotelRepository = Mockito.mock(HotelRepository.class);
    reservationConnector = Mockito.mock(ReservationConnector.class);
    this.roomService = new RoomServiceImpl(roomTypeRepository, roomRepository, hotelRepository, reservationConnector);
  }

  @Test
  void getAllRoomType_shouldWork() {
    var roomTypeEntity1 = RoomType.builder()
        .id(ROOM_TYPE_ID_1)
        .name(ROOM_TYPE_NAME_1)
        .numberPeople(NUMBER_PERSON_PER_ROOM)
        .build();
    var roomTypeEntity2 = RoomType.builder()
        .id(ROOM_TYPE_ID_2)
        .name(ROOM_TYPE_NAME_1)
        .numberPeople(NUMBER_PERSON_PER_ROOM)
        .build();
    var roomType1 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_1)
        .type(ROOM_TYPE_NAME_1)
        .numberPersonPerRoom(NUMBER_PERSON_PER_ROOM)
        .build();
    var roomType2 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_2)
        .type(ROOM_TYPE_NAME_1)
        .numberPersonPerRoom(NUMBER_PERSON_PER_ROOM)
        .build();
    var roomTypeEntities = List.of(roomTypeEntity1, roomTypeEntity2);
    var expected = List.of(roomType1, roomType2);
    Mockito.when(roomTypeRepository.findAll()).thenReturn(roomTypeEntities);
    var result = roomService.getAllRoomType();
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
        .hotelId(HOTEL_ID)
        .typeId(ROOM_TYPE_ID_1)
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .build();
    var roomTypeEntity = RoomType.builder()
        .name(ROOM_NAME_1)
        .id(request.getTypeId())
        .numberPeople(NUMBER_AVAILABLE)
        .build();
    var hotelEntity = Hotel.builder()
        .id(request.getHotelId())
        .address(HOTEL_ADDRESS)
        .name(HOTEL_NAME)
        .build();
    var roomEntity = Room.builder()
        .roomType(roomTypeEntity)
        .hotel(hotelEntity)
        .description(request.getDescription())
        .name(request.getName())
        .status(RoomStatus.PLANNED)
        .build();
    var expected = RoomInfoResponse.builder()
        .id(ROOM_ID_1)
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .type(ROOM_TYPE_NAME_1)
        .build();
    Mockito.when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.of(hotelEntity));
    Mockito.when(roomTypeRepository.findById(request.getTypeId())).thenReturn(Optional.of(roomTypeEntity));
    Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
    var result = roomService.createRoom(request);
    Assertions.assertNotNull(expected.getId());
    Assertions.assertEquals(expected.getName(), result.getName());
    Assertions.assertEquals(expected.getDescription(), result.getDescription());
  }

  @Test
  void createRoom_shouldThrow_NotFound_whenFindHotelById() {
    var request = CreateRoomRequest.builder()
        .hotelId(HOTEL_ID)
        .typeId(ROOM_TYPE_ID_1)
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .build();
    var hotelEntity = Hotel.builder()
        .id(request.getHotelId())
        .address(HOTEL_ADDRESS)
        .name(HOTEL_NAME)
        .build();
    Mockito.when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.of(hotelEntity));
    Mockito.when(roomTypeRepository.findById(request.getTypeId())).thenReturn(Optional.empty());
    Assertions.assertThrows(HotelServiceException.class, () -> roomService.createRoom(request));
  }

  @Test
  void createRoom_shouldThrow_NotFound_whenFindRoomTypeById() {
    var request = CreateRoomRequest.builder()
        .hotelId(HOTEL_ID)
        .typeId(ROOM_TYPE_ID_1)
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .build();
    var roomTypeEntity = RoomType.builder()
        .name(ROOM_NAME_1)
        .id(request.getTypeId())
        .numberPeople(NUMBER_AVAILABLE)
        .build();
    Mockito.when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.empty());
    Mockito.when(roomTypeRepository.findById(request.getTypeId())).thenReturn(Optional.of(roomTypeEntity));
    Assertions.assertThrows(HotelServiceException.class, () -> roomService.createRoom(request));
  }

  @Test
  void updateRoom_shouldWork() {
    var request = UpdateRoomRequest.builder()
        .description(DESCRIPTION)
        .build();
    var roomTypeEntity = RoomType.builder()
        .name(ROOM_NAME_1)
        .id(ROOM_TYPE_ID_1)
        .numberPeople(NUMBER_AVAILABLE)
        .build();
    var hotelEntity = Hotel.builder()
        .id(HOTEL_ID)
        .address(HOTEL_ADDRESS)
        .name(HOTEL_NAME)
        .build();
    var roomEntity = Room.builder()
        .id(ROOM_ID_1)
        .name(ROOM_NAME_1)
        .roomType(roomTypeEntity)
        .hotel(hotelEntity)
        .build();
    var expected = RoomInfoResponse.builder()
        .id(ROOM_ID_1)
        .name(ROOM_NAME_1)
        .description(DESCRIPTION)
        .type(ROOM_TYPE_NAME_1)
        .build();
    Mockito.when(roomRepository.findById(ROOM_ID_1)).thenReturn(Optional.of(roomEntity));
    roomEntity.setDescription(request.getDescription());
    Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
    var result = roomService.updateRoom(ROOM_ID_1, request);
    Assertions.assertNotNull(expected.getId());
    Assertions.assertEquals(expected.getName(), result.getName());
    Assertions.assertEquals(expected.getDescription(), result.getDescription());
  }

  @Test
  void getAllRoomByTypeAndArrangeTime_shouldWork() {
    var hotelEntity = Hotel.builder()
        .id(HOTEL_ID)
        .address(HOTEL_ADDRESS)
        .name(HOTEL_NAME)
        .build();
    var roomEntity1 = Room.builder()
        .id(ROOM_ID_1)
        .name(ROOM_NAME_1)
        .hotel(hotelEntity)
        .description(DESCRIPTION)
        .build();
    var roomEntity2 = Room.builder()
        .id(ROOM_ID_2)
        .name(ROOM_NAME_2)
        .hotel(hotelEntity)
        .description(DESCRIPTION)
        .build();
    var roomEntities = List.of(roomEntity1, roomEntity2);
    var roomTypeEntity1 = RoomType.builder()
        .name(ROOM_NAME_1)
        .id(ROOM_TYPE_ID_1)
        .numberPeople(NUMBER_AVAILABLE)
        .rooms(roomEntities)
        .build();
    var roomTypeEntity2 = RoomType.builder()
        .name(ROOM_NAME_2)
        .id(ROOM_TYPE_ID_2)
        .numberPeople(NUMBER_AVAILABLE)
        .rooms(roomEntities)
        .build();

    var roomTypeResponse1 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_1)
        .numberPersonPerRoom(NUMBER_AVAILABLE)
        .type(ROOM_NAME_1)
        .build();

    var roomTypeResponse2 = RoomTypeResponse.builder()
        .id(ROOM_TYPE_ID_2)
        .numberPersonPerRoom(NUMBER_AVAILABLE)
        .type(ROOM_NAME_2)
        .build();

    var roomTypeEntities = List.of(roomTypeEntity1, roomTypeEntity2);
    var expected = List.of(roomTypeResponse1, roomTypeResponse2);
    Mockito.when(roomTypeRepository.findAll()).thenReturn(roomTypeEntities);
    Mockito.when(reservationConnector.getAllReservationInTime(this.from, this.to))
        .thenReturn(Collections.emptyList());
    var result = roomService.getAllRoomInArrangeTime(this.from, this.to);
    Assertions.assertEquals(expected.size(), result.size());
    Assertions.assertEquals(expected.get(0).getType(), result.get(0).getType());
    Assertions.assertEquals(expected.get(0).getNumberPersonPerRoom(), result.get(0).getNumberPersonPerRoom());
    Assertions.assertEquals(expected.get(1).getType(), result.get(1).getType());
    Assertions.assertEquals(expected.get(1).getNumberPersonPerRoom(), result.get(1).getNumberPersonPerRoom());
  }
}
