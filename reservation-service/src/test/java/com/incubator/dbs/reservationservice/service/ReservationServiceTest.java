package com.incubator.dbs.reservationservice.service;

import com.incubator.dbs.reservationservice.exception.ReservationServiceException;
import com.incubator.dbs.reservationservice.model.constant.ReservationStatus;
import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.dto.HotelInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.RoomTypeResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.dto.UserInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.entity.Reservation;
import com.incubator.dbs.reservationservice.repository.ReservationRepository;
import com.incubator.dbs.reservationservice.service.connector.GuestServiceConnector;
import com.incubator.dbs.reservationservice.service.connector.HotelServiceConnector;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  private final ReservationService reservationService;
  private final ReservationRepository reservationRepository;
  private final HotelServiceConnector hotelServiceConnector;
  private final GuestServiceConnector guestServiceConnector;
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
  private static final Integer NUMBER_AVAILABLE = 10;
  private static final Double TOTAL = RandomUtils.nextDouble();

  public ReservationServiceTest() {
    reservationRepository = Mockito.mock(ReservationRepository.class);
    hotelServiceConnector = Mockito.mock(HotelServiceConnector.class);
    guestServiceConnector = Mockito.mock(GuestServiceConnector.class);
    this.reservationService = new ReservationServiceImpl(reservationRepository, hotelServiceConnector,
        guestServiceConnector);
  }

  @Test
  void create_shouldWork() {
    var request = CreateReservationRequestDTO.builder()
        .userId(USER_ID.toString())
        .roomTypeId(ROOM_TYPE_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .hotelId(HOTEL_ID_1)
        .from(from)
        .to(to)
        .build();

    var hotelInfo = HotelInfoResponseDTO.builder()
        .id(HOTEL_ID_1)
        .build();

    var roomType = RoomTypeResponseDTO.builder()
        .id(ROOM_TYPE_ID_1)
        .numberAvailable(NUMBER_AVAILABLE)
        .hotelInfo(List.of(hotelInfo))
        .price(1.0)
        .build();

    var entity = Reservation.builder()
        .createdTime(Instant.now())
        .userId(UUID.fromString(request.getUserId()))
        .hotelId(request.getHotelId())
        .numberRooms(request.getNumberRooms())
        .roomTypeId(request.getRoomTypeId())
        .status(ReservationStatus.PENDING)
        .build();

    var userInfo = UserInfoResponseDTO.builder()
        .id(USER_ID)
        .build();

    var expected = ReservationInfoResponseDTO.builder()
        .roomTypeId(entity.getRoomTypeId())
        .id(entity.getId())
        .to(entity.getToDate())
        .from(entity.getFromDate())
        .hotelId(entity.getHotelId())
        .status(entity.getStatus())
        .build();

    Mockito.when(hotelServiceConnector.getAllRoomType()).thenReturn(List.of(roomType));
    Mockito.when(guestServiceConnector.getUserInfo(USER_ID.toString())).thenReturn(Optional.of(userInfo));
    Mockito.when(reservationRepository.save(Mockito.any())).thenReturn(entity);
    var result = reservationService.create(request);
    Assertions.assertEquals(expected.getHotelId(), result.getHotelId());
    Assertions.assertEquals(expected.getStatus(), result.getStatus());
    Assertions.assertEquals(expected.getRoomTypeId(), result.getRoomTypeId());

  }

  @Test
  void create_shouldThrow_NotFound_WhenInputInvalidUserId() {
    var request = CreateReservationRequestDTO.builder()
        .userId(USER_ID.toString())
        .roomTypeId(ROOM_TYPE_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .hotelId(HOTEL_ID_1)
        .from(from)
        .to(to)
        .build();
    Mockito.when(guestServiceConnector.getUserInfo(USER_ID.toString())).thenReturn(Optional.empty());
    Assertions.assertThrows(ReservationServiceException.class, () -> reservationService.create(request));
  }

  @Test
  void create_shouldThrow_NotFound_WhenInputInvalidRoomTypeId() {
    var request = CreateReservationRequestDTO.builder()
        .userId(USER_ID.toString())
        .roomTypeId(ROOM_TYPE_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .hotelId(HOTEL_ID_1)
        .build();

    var userInfo = UserInfoResponseDTO.builder()
        .id(USER_ID)
        .build();
    Mockito.when(guestServiceConnector.getUserInfo(USER_ID.toString())).thenReturn(Optional.of(userInfo));
    Mockito.when(hotelServiceConnector.getAllRoomType()).thenReturn(List.of());
    Assertions.assertThrows(ReservationServiceException.class, () -> reservationService.create(request));
  }

  @Test
  void create_shouldThrow_NotFound_WhenInputInvalidHotelId() {
    var request = CreateReservationRequestDTO.builder()
        .userId(USER_ID.toString())
        .roomTypeId(ROOM_TYPE_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .hotelId(HOTEL_ID_1)
        .build();

    var userInfo = UserInfoResponseDTO.builder()
        .id(USER_ID)
        .build();

    var roomType = RoomTypeResponseDTO.builder()
        .id(ROOM_TYPE_ID_1)
        .numberAvailable(NUMBER_AVAILABLE)
        .hotelInfo(List.of())
        .price(1.0)
        .build();

    Mockito.when(guestServiceConnector.getUserInfo(USER_ID.toString())).thenReturn(Optional.of(userInfo));
    Mockito.when(hotelServiceConnector.getAllRoomType()).thenReturn(List.of(roomType));
    Assertions.assertThrows(ReservationServiceException.class, () -> reservationService.create(request));
  }

  @Test
  void update_shouldWork() {
    var request = UpdateReservationRequestDTO.builder()
        .status(ReservationStatus.PAID.getValue())
        .build();
    var entity = Reservation.builder()
        .status(ReservationStatus.PENDING)
        .build();
    Mockito.when(reservationRepository.findById(RESERVATION_ID_1)).thenReturn(Optional.of(entity));
    entity.setStatus(ReservationStatus.valueOf(request.getStatus()));
    entity.setUpdateTime(Instant.now());
    Mockito.when(reservationRepository.save(entity)).thenReturn(entity);
    var expected = ReservationInfoResponseDTO.builder()
        .status(entity.getStatus())
        .build();
    var result = reservationService.update(RESERVATION_ID_1, request);
    Assertions.assertEquals(expected.getStatus(), result.getStatus());
    Assertions.assertEquals(expected.getStatus(), ReservationStatus.PAID);
  }

  @Test
  void update_shouldThrow_NotFound_WithInvalidReservationId() {
    var request = UpdateReservationRequestDTO.builder()
        .status(ReservationStatus.PAID.getValue())
        .build();
    Mockito.when(reservationRepository.findById(RESERVATION_ID_1)).thenReturn(Optional.empty());
    Assertions.assertThrows(ReservationServiceException.class,
        () -> reservationService.update(RESERVATION_ID_1, request));
  }

  @Test
  void delete_shouldWord() {
    reservationService.delete(RESERVATION_ID_1);
    Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(RESERVATION_ID_1);
  }

  @Test
  void getByUser_shouldWork() {
    var entity1 = Reservation.builder()
        .createdTime(Instant.now())
        .userId(USER_ID)
        .hotelId(HOTEL_ID_1)
        .numberRooms(NUMBER_AVAILABLE)
        .roomTypeId(ROOM_TYPE_ID_1)
        .status(ReservationStatus.PENDING)
        .build();
    var entity2 = Reservation.builder()
        .createdTime(Instant.now())
        .userId(USER_ID)
        .hotelId(HOTEL_ID_2)
        .numberRooms(NUMBER_AVAILABLE)
        .roomTypeId(ROOM_TYPE_ID_2)
        .status(ReservationStatus.PAID)
        .build();
    var reservation1 = ReservationInfoResponseDTO.builder()
        .createdTime(entity1.getCreatedTime())
        .hotelId(entity1.getHotelId())
        .numberRooms(entity1.getNumberRooms())
        .roomTypeId(entity1.getRoomTypeId())
        .status(entity1.getStatus())
        .build();
    var reservation2 = ReservationInfoResponseDTO.builder()
        .createdTime(entity2.getCreatedTime())
        .hotelId(entity2.getHotelId())
        .numberRooms(entity2.getNumberRooms())
        .roomTypeId(entity2.getRoomTypeId())
        .status(entity2.getStatus())
        .build();
    var userReservationEntities = List.of(entity1, entity2);
    var expected = List.of(reservation1, reservation2);
    Mockito.when(reservationRepository.findAllByUserId(USER_ID)).thenReturn(userReservationEntities);
    var result = reservationService.getByUser(USER_ID.toString());

    Assertions.assertEquals(expected, result);

  }

  @Test
  void get_shouldWork() {
    var entity1 = Reservation.builder()
        .createdTime(Instant.now())
        .userId(USER_ID)
        .hotelId(HOTEL_ID_1)
        .fromDate(from)
        .toDate(to)
        .numberRooms(NUMBER_AVAILABLE)
        .roomTypeId(ROOM_TYPE_ID_1)
        .status(ReservationStatus.PENDING)
        .build();
    var entity2 = Reservation.builder()
        .createdTime(Instant.now())
        .userId(USER_ID)
        .hotelId(HOTEL_ID_2)
        .fromDate(from)
        .toDate(to)
        .numberRooms(NUMBER_AVAILABLE)
        .roomTypeId(ROOM_TYPE_ID_2)
        .status(ReservationStatus.PAID)
        .build();
    var reservation1 = ReservationInfoResponseDTO.builder()
        .createdTime(entity1.getCreatedTime())
        .hotelId(entity1.getHotelId())
        .to(entity1.getToDate())
        .from(entity1.getFromDate())
        .numberRooms(entity1.getNumberRooms())
        .roomTypeId(entity1.getRoomTypeId())
        .status(entity1.getStatus())
        .build();
    var reservation2 = ReservationInfoResponseDTO.builder()
        .createdTime(entity2.getCreatedTime())
        .hotelId(entity2.getHotelId())
        .numberRooms(entity2.getNumberRooms())
        .to(entity2.getToDate())
        .from(entity2.getFromDate())
        .roomTypeId(entity2.getRoomTypeId())
        .status(entity2.getStatus())
        .build();
    var userReservationEntities = List.of(entity1, entity2);
    var expected = List.of(reservation1, reservation2);
    Mockito.when(reservationRepository.findAllByFromDateBeforeAndToDateAfter(from, to))
        .thenReturn(userReservationEntities);
    var result = reservationService.get(from, to);

    Assertions.assertEquals(expected, result);
  }
}
