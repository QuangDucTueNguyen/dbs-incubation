package com.incubator.dbs.hotelservice.service;

import com.incubator.dbs.hotelservice.exception.HotelErrorResponse;
import com.incubator.dbs.hotelservice.exception.HotelServiceException;
import com.incubator.dbs.hotelservice.model.constant.RoomStatus;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

/**
 * Room Service
 */
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

  private final RoomTypeRepository roomTypeRepository;
  private final RoomRepository roomRepository;
  private final HotelRepository hotelRepository;
  private final ReservationConnector reservationConnector;

  public RoomServiceImpl(RoomTypeRepository roomTypeRepository,
      RoomRepository roomRepository, HotelRepository hotelRepository,
      ReservationConnector reservationConnector) {
    this.roomTypeRepository = roomTypeRepository;
    this.roomRepository = roomRepository;
    this.hotelRepository = hotelRepository;
    this.reservationConnector = reservationConnector;
  }

  @Override
  public List<RoomTypeResponse> getAllRoomType() {
    log.info("Get all room type");
    var result = roomTypeRepository.findAll().spliterator();
    return StreamSupport.stream(result, false)
        .map(this::convertToRoomTypeResponse)
        .collect(Collectors.toList());
  }

  @Override
  public RoomInfoResponse createRoom(CreateRoomRequest createRoomRequest) {
    log.info("Create room: {}", createRoomRequest);
    var hotel = hotelRepository.findById(createRoomRequest.getHotelId())
        .orElseThrow(() -> {
          log.error("Can not find hotel: {}", createRoomRequest.getHotelId());
          return new HotelServiceException(HotelErrorResponse.NOT_FOUND);
        });
    var roomType = roomTypeRepository.findById(createRoomRequest.getTypeId())
        .orElseThrow(() -> {
          log.error("Can not find room type: {}", createRoomRequest.getTypeId());
          return new HotelServiceException(HotelErrorResponse.NOT_FOUND);
        });
    var roomEntity = Room.builder()
        .roomType(roomType)
        .hotel(hotel)
        .name(createRoomRequest.getName())
        .description(createRoomRequest.getDescription())
        .status(RoomStatus.PLANNED)
        .build();
    return convertToRoomInfo(roomRepository.save(roomEntity));
  }

  @Override
  public RoomInfoResponse updateRoom(Integer id, UpdateRoomRequest updateRoomRequest) {
    log.info("Update room [{}] - request {}", id, updateRoomRequest);
    var room = roomRepository.findById(id)
        .orElseThrow(() -> {
          log.error("Can not find room id [{}] .", id);
          return new HotelServiceException(HotelErrorResponse.NOT_FOUND);
        });
    Optional.ofNullable(updateRoomRequest.getDescription()).ifPresent(room::setDescription);
    Optional.ofNullable(updateRoomRequest.getName()).ifPresent(room::setName);
    return convertToRoomInfo(roomRepository.save(room));
  }

  @Override
  public List<RoomTypeResponse> getAllRoomInArrangeTime(Instant from, Instant to) {
    log.info("Get all room in arrange time from-{} to-{} .", from, to);
    var roomType = roomTypeRepository.findAll();
    var reservation = reservationConnector.getAllReservationInTime(from, to);
    return StreamSupport.stream(roomType.spliterator(), false)
        .map(rt -> {
          int numberAvailable = rt.getRooms().size() - reservation.stream()
              .filter(rs -> rs.getRoomTypeId().equals(rt.getId()))
              .map(ReservationResponse::getNumberRooms)
              .findFirst()
              .orElse(NumberUtils.INTEGER_ZERO);
          var rs = convertToRoomTypeResponse(rt);
          rs.setNumberAvailable(numberAvailable);
          return rs;
        }).collect(Collectors.toList());
  }

  private RoomTypeResponse convertToRoomTypeResponse(RoomType roomType){
    return RoomTypeResponse.builder()
        .type(roomType.getName())
        .numberPersonPerRoom(roomType.getNumberPeople())
        .id(roomType.getId())
        .price(roomType.getPrice())
        .hotelInfo(Optional.ofNullable(roomType.getRooms()).map(this::toHotelResponse).orElse(Collections.emptyList()))
        .build();
  }

  private List<CreateHotelResponse> toHotelResponse(List<Room> rooms) {
    return rooms.stream()
        .map(Room::getHotel)
        .map(this::convertToCreateHotelResponse)
        .distinct()
        .collect(Collectors.toList());
  }

  private RoomInfoResponse convertToRoomInfo(Room room){
    return RoomInfoResponse.builder()
        .id(room.getId())
        .name(room.getName())
        .numberPersonPerRoom(room.getRoomType().getNumberPeople())
        .hotelId(room.getHotel().getId())
        .type(room.getRoomType().getName())
        .typeId(room.getRoomType().getId())
        .hotelName(room.getHotel().getName())
        .description(room.getDescription())
        .status(room.getStatus())
        .price(room.getRoomType().getPrice())
        .build();
  }

  private CreateHotelResponse convertToCreateHotelResponse(Hotel hotel) {
    return CreateHotelResponse.builder()
        .name(hotel.getName())
        .address(hotel.getAddress())
        .id(hotel.getId())
        .hotline(hotel.getHotline())
        .build();
  }
}
