package com.incubator.dbs.hotelservice.controller;

import com.incubator.dbs.hotelservice.model.dto.CreateHotelRequest;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.service.HotelService;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelControllerTest {

  private final HotelOperations hotelOperations;
  private final HotelService hotelService;
  private final Integer HOTEL_ID = Integer.MAX_VALUE;
  private final String HOTEL_NAME = "HOTEL_NAME";
  private final Integer ROOM_ID = new Random().nextInt();
  private final String ROOM_NAME = "ROOM_NAME";
  private final String HOTEL_ADDRESS = "HOTEL_ADDRESS";
  private final String HOTEL_HOTLINE = "123456789";

  public HotelControllerTest() {
    hotelService = Mockito.mock(HotelService.class);
    hotelOperations = new HotelController(hotelService);
  }

  @Test
  void getRoomByHotelId_shouldWork() {
    var expected = RoomInfoResponse.builder()
        .hotelId(HOTEL_ID)
        .hotelName(HOTEL_NAME)
        .id(ROOM_ID)
        .name(ROOM_NAME)
        .build();
    Mockito.when(hotelService.getRoom(HOTEL_ID, ROOM_ID)).thenReturn(expected);
    var result = hotelOperations.getRoom(HOTEL_ID, ROOM_ID);
    Assertions.assertEquals(expected.getHotelId(), result.getHotelId());
    Assertions.assertEquals(expected.getHotelName(), result.getHotelName());
    Assertions.assertEquals(expected.getId(), result.getId());
    Assertions.assertEquals(expected.getName(), result.getName());
  }

  @Test
  void createHotel_shouldWork(){
    var request = CreateHotelRequest.builder()
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
    Mockito.when(hotelService.createHotel(request)).thenReturn(expected);
    var result = hotelOperations.createHotel(request);
    Assertions.assertNotNull(expected.getId());
    Assertions.assertEquals(expected.getAddress(), result.getAddress());
    Assertions.assertEquals(expected.getHotline(), result.getHotline());
    Assertions.assertEquals(expected.getName(), result.getName());
  }
}
