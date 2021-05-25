package com.incubator.dbs.hotelservice.service;

import com.incubator.dbs.hotelservice.exception.HotelServiceException;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelRequest;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.model.entity.Hotel;
import com.incubator.dbs.hotelservice.model.entity.Room;
import com.incubator.dbs.hotelservice.model.entity.RoomType;
import com.incubator.dbs.hotelservice.repository.HotelRepository;
import com.incubator.dbs.hotelservice.repository.RoomRepository;
import java.util.Optional;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

  private final HotelRepository hotelRepository;
  private final RoomRepository roomRepository;
  private final HotelService hotelService;
  private final Integer HOTEL_ID = Integer.MAX_VALUE;
  private final String HOTEL_NAME = "HOTEL_NAME";
  private final Integer ROOM_ID = new Random().nextInt();
  private final String ROOM_NAME = "ROOM_NAME";
  private final String HOTEL_ADDRESS = "HOTEL_ADDRESS";
  private final String HOTEL_HOTLINE = "123456789";
  private final Integer ROOM_TYPE_ID = new Random().nextInt();
  private final String ROOM_TYPE_NAME = "ROOM_TYPE_NAME";
  private final Integer NUMBER_AVAILABLE = 5;
  private final String DESCRIPTION = RandomStringUtils.randomAlphabetic(1000);

  public HotelServiceTest() {
    hotelRepository = Mockito.mock(HotelRepository.class);
    roomRepository = Mockito.mock(RoomRepository.class);
    hotelService = new HotelServiceImpl(hotelRepository, roomRepository);
  }

  @Test
  void getRoom_shouldWork() {
    var roomTypeEntity = RoomType.builder()
        .name(ROOM_TYPE_NAME)
        .id(ROOM_TYPE_ID)
        .numberPeople(NUMBER_AVAILABLE)
        .build();
    var hotelEntity = Hotel.builder()
        .id(HOTEL_ID)
        .address(HOTEL_ADDRESS)
        .name(HOTEL_NAME)
        .build();
    var roomEntity = Room.builder()
        .roomType(roomTypeEntity)
        .hotel(hotelEntity)
        .id(ROOM_ID)
        .name(ROOM_NAME)
        .description(DESCRIPTION)
        .build();
    var expected = RoomInfoResponse.builder()
        .hotelId(HOTEL_ID)
        .hotelName(HOTEL_NAME)
        .id(ROOM_ID)
        .name(ROOM_NAME)
        .build();
    Mockito.when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.of(roomEntity));
    var result = hotelService.getRoom(HOTEL_ID, ROOM_ID);
    Assertions.assertEquals(expected.getHotelId(), result.getHotelId());
    Assertions.assertEquals(expected.getHotelName(), result.getHotelName());
    Assertions.assertEquals(expected.getId(), result.getId());
    Assertions.assertEquals(expected.getName(), result.getName());
  }

  @Test
  void getRoom_shouldThrow_NotFound() {
    Mockito.when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.empty());
    Assertions.assertThrows(HotelServiceException.class, () -> hotelService.getRoom(HOTEL_ID, ROOM_ID));
  }

  @Test
  void createHotel_shouldWork() {
    var request = CreateHotelRequest.builder()
        .address(HOTEL_ADDRESS)
        .hotline(HOTEL_HOTLINE)
        .name(HOTEL_NAME)
        .build();
    var hotelEntity = Hotel.builder()
        .address(HOTEL_ADDRESS)
        .hotline(HOTEL_HOTLINE)
        .name(HOTEL_NAME)
        .build();
    var expected = CreateHotelResponse.builder()
        .address(HOTEL_ADDRESS)
        .hotline(HOTEL_HOTLINE)
        .name(HOTEL_NAME)
        .id(HOTEL_ID)
        .build();
    Mockito.when(hotelRepository.save(hotelEntity)).thenReturn(hotelEntity);
    var result = hotelService.createHotel(request);

    Assertions.assertEquals(expected.getAddress(), result.getAddress());
    Assertions.assertEquals(expected.getHotline(), result.getHotline());
    Assertions.assertEquals(expected.getName(), result.getName());
  }
}
