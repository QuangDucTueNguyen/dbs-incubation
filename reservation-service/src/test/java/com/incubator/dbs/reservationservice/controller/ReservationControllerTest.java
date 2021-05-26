package com.incubator.dbs.reservationservice.controller;

import com.incubator.dbs.reservationservice.model.constant.ReservationStatus;
import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequest;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponse;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequest;
import com.incubator.dbs.reservationservice.service.ReservationService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

  private final ReservationService reservationService;
  private final ReservationController reservationController;
  private static final Instant from = Instant.now();
  private static final Instant to = from.plusMillis(5 * 24 * 60 * 60 * 100);
  private static final UUID USER_ID = UUID.randomUUID();
  private static final UUID RESERVATION_ID_1 = UUID.randomUUID();
  private static final UUID RESERVATION_ID_2 = UUID.randomUUID();
  private static final Integer HOTEL_ID_1 = RandomUtils.nextInt();
  private static final Integer HOTEL_ID_2 = RandomUtils.nextInt();
  private static final Integer ROOM_TYPE_ID_1 = RandomUtils.nextInt();
  private static final Integer ROOM_TYPE_ID_2 = RandomUtils.nextInt();
  private static final Integer NUMBER_ROOMS = 5;
  private static final Double TOTAL = RandomUtils.nextDouble();

  public ReservationControllerTest() {
    reservationService = Mockito.mock(ReservationService.class);
    reservationController = new ReservationController(reservationService);
  }

  @Test
  void create_shouldWork() {
    var request = CreateReservationRequest.builder()
        .from(from)
        .to(to)
        .hotelId(HOTEL_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .roomTypeId(ROOM_TYPE_ID_1)
        .userId(USER_ID.toString())
        .build();
    var expected = ReservationInfoResponse.builder()
        .status(ReservationStatus.PENDING)
        .from(from)
        .to(to)
        .numberRooms(NUMBER_ROOMS)
        .hotelId(HOTEL_ID_1)
        .roomTypeId(ROOM_TYPE_ID_1)
        .id(RESERVATION_ID_1)
        .total(TOTAL)
        .build();
    Mockito.when(reservationService.create(request)).thenReturn(expected);
    var result = reservationController.create(request);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(expected.getFrom(), result.getFrom());
    Assertions.assertEquals(expected.getTo(), result.getTo());
    Assertions.assertEquals(expected.getHotelId(), result.getHotelId());
    Assertions.assertEquals(expected.getRoomTypeId(), result.getRoomTypeId());
    Assertions.assertEquals(expected.getNumberRooms(), result.getNumberRooms());
    Assertions.assertEquals(expected.getStatus(), ReservationStatus.PENDING);
    Assertions.assertEquals(expected.getTotal(), TOTAL);
  }

  @Test
  void update_shouldWork() {
    var id = RESERVATION_ID_1;
    var request = UpdateReservationRequest.builder()
        .status(ReservationStatus.PAID.getValue())
        .build();
    var expected = ReservationInfoResponse.builder()
        .id(id)
        .status(ReservationStatus.PAID)
        .build();
    Mockito.when(reservationService.update(id.toString(), request)).thenReturn(expected);
    var result = reservationController.update(id.toString(), request);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(expected.getStatus(), ReservationStatus.PAID);
  }

  @Test
  void delete_shouldWork() {
    var id = RESERVATION_ID_1.toString();
    reservationController.delete(id);
    Mockito.verify(reservationService, Mockito.times(1)).delete(id);
  }

  @Test
  void getByUser_shouldWork() {
    var reservation1 = ReservationInfoResponse.builder()
        .hotelId(HOTEL_ID_1)
        .roomTypeId(ROOM_TYPE_ID_1)
        .id(RESERVATION_ID_1)
        .build();
    var reservation2 = ReservationInfoResponse.builder()
        .hotelId(HOTEL_ID_2)
        .roomTypeId(ROOM_TYPE_ID_2)
        .id(RESERVATION_ID_2)
        .build();
    var expected = List.of(reservation1, reservation2);
    Mockito.when(reservationService.getByUser(USER_ID.toString())).thenReturn(expected);
    var result = reservationController.getByUser(USER_ID.toString());

    Assertions.assertEquals(expected.size(), result.size());
    Assertions.assertEquals(expected.get(0), result.get(0));
    Assertions.assertEquals(expected.get(1), result.get(1));

  }

  @Test
  void get_shouldWork() {
    var reservation1 = ReservationInfoResponse.builder()
        .from(from)
        .to(to)
        .hotelId(HOTEL_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .roomTypeId(ROOM_TYPE_ID_1)
        .id(RESERVATION_ID_1)
        .build();
    var reservation2 = ReservationInfoResponse.builder()
        .from(from)
        .to(to)
        .hotelId(HOTEL_ID_2)
        .numberRooms(NUMBER_ROOMS)
        .roomTypeId(ROOM_TYPE_ID_2)
        .id(RESERVATION_ID_2)
        .build();
    var expected = List.of(reservation1, reservation2);
    Mockito.when(reservationService.get(from, to)).thenReturn(expected);
    var result = reservationController.get(from, to);
    Assertions.assertEquals(expected.size(), result.size());
    Assertions.assertEquals(expected.get(0), result.get(0));
    Assertions.assertEquals(expected.get(1), result.get(1));
  }
}
