package com.incubator.dbs.reservationservice.service;

import com.incubator.dbs.reservationservice.exception.ReservationErrorResponse;
import com.incubator.dbs.reservationservice.exception.ReservationServiceException;
import com.incubator.dbs.reservationservice.model.constant.ReservationStatus;
import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.entity.Reservation;
import com.incubator.dbs.reservationservice.repository.ReservationRepository;
import com.incubator.dbs.reservationservice.service.connector.GuestServiceConnector;
import com.incubator.dbs.reservationservice.service.connector.HotelServiceConnector;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final HotelServiceConnector hotelServiceConnector;
  private final GuestServiceConnector guestServiceConnector;

  public ReservationServiceImpl(
      ReservationRepository reservationRepository,
      HotelServiceConnector hotelServiceConnector,
      GuestServiceConnector guestServiceConnector) {
    this.reservationRepository = reservationRepository;
    this.hotelServiceConnector = hotelServiceConnector;
    this.guestServiceConnector = guestServiceConnector;
  }

  @Override
  public ReservationInfoResponseDTO create(CreateReservationRequestDTO request) {
    log.info("Create a reservation: {}", request);
    var userInfo = guestServiceConnector.getUserInfo(request.getUserId())
        .orElseThrow(() -> {
          log.error("Can not find user [{}]", request.getUserId());
          return new ReservationServiceException(ReservationErrorResponse.NOT_FOUND);
        });
    var roomType = hotelServiceConnector.getAllRoomType()
        .stream()
        .filter(rt -> Objects.equals(rt.getId(), request.getRoomTypeId()))
        .findFirst()
        .orElseThrow(() -> {
          log.error("Can not find roomType [{}]", request.getRoomTypeId());
          return new ReservationServiceException(ReservationErrorResponse.NOT_FOUND);
        });
    var hotel = roomType.getHotelInfo()
        .stream()
        .filter(ht -> Objects.equals(ht.getId(), request.getHotelId()))
        .findFirst()
        .orElseThrow(() -> {
          log.error("Can not find hotel [{}]", request.getHotelId());
          return new ReservationServiceException(ReservationErrorResponse.NOT_FOUND);
        });

    var entity = Reservation.builder()
        .createdTime(Instant.now())
        .status(ReservationStatus.PENDING)
        .roomTypeId(roomType.getId())
        .hotelId(hotel.getId())
        .userId(userInfo.getId())
        .fromDate(request.getFrom())
        .toDate(request.getTo())
        .numberRooms(request.getNumberRooms())
        .total(request.getNumberRooms() * roomType.getPrice())
        .build();

    return toReservationInfo(reservationRepository.save(entity));
  }

  @Override
  public ReservationInfoResponseDTO update(String id, UpdateReservationRequestDTO request) {
    log.info("Update reservation {} with body {}", id, request);
    return reservationRepository.findById(id)
        .map(rt -> {
          rt.setStatus(ReservationStatus.valueOf(request.getStatus()));
          return toReservationInfo(reservationRepository.save(rt));
        })
        .orElseThrow(() -> {
          log.error("Can not find reservation [{}]", id);
          return new ReservationServiceException(ReservationErrorResponse.NOT_FOUND);
        });
  }

  @Override
  public void delete(String id) {
    log.info("Delete reservation {}", id);
    reservationRepository.deleteById(id);
  }

  @Override
  public List<ReservationInfoResponseDTO> getByUser(String id) {
    log.info("Get reservation by user id {}", id);
    return reservationRepository.findAllByUserId(UUID.fromString(id))
        .stream()
        .map(this::toReservationInfo)
        .collect(Collectors.toList());
  }

  @Override
  public List<ReservationInfoResponseDTO> get(Instant from, Instant to) {
    log.info("Get reservation from {} - to {}", from, to);
    return reservationRepository.findAllByInArrangeTime(from, to)
        .stream()
        .map(this::toReservationInfo)
        .collect(Collectors.toList());
  }

  private ReservationInfoResponseDTO toReservationInfo(Reservation reservation) {
    return ReservationInfoResponseDTO.builder()
        .id(reservation.getId())
        .roomTypeId(reservation.getRoomTypeId())
        .to(reservation.getToDate())
        .from(reservation.getFromDate())
        .createdTime(reservation.getCreatedTime())
        .hotelId(reservation.getHotelId())
        .total(reservation.getTotal())
        .status(reservation.getStatus())
        .numberRooms(reservation.getNumberRooms())
        .build();
  }
}
