package com.incubator.dbs.hotelservice.service;

import com.incubator.dbs.hotelservice.exception.HotelErrorResponse;
import com.incubator.dbs.hotelservice.exception.HotelServiceException;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelRequest;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.model.entity.Hotel;
import com.incubator.dbs.hotelservice.model.entity.Room;
import com.incubator.dbs.hotelservice.repository.HotelRepository;
import com.incubator.dbs.hotelservice.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final RoomRepository roomRepository;

  public HotelServiceImpl(HotelRepository hotelRepository,
      RoomRepository roomRepository) {
    this.hotelRepository = hotelRepository;
    this.roomRepository = roomRepository;
  }

  @Override
  public CreateHotelResponse createHotel(CreateHotelRequest createHotelRequest) {
    log.info("Create hotel with info: {}", createHotelRequest);
    var hotelEntity = Hotel.builder()
        .name(createHotelRequest.getName())
        .hotline(createHotelRequest.getHotline())
        .address(createHotelRequest.getAddress())
        .build();
    return convertToCreateHotelResponse(hotelRepository.save(hotelEntity));
  }

  @Override
  public RoomInfoResponse getRoom(Integer hotelId, Integer roomId) {
    log.info("Get room [{}] in hotel [{}]", roomId, hotelId);
    return roomRepository.findById(roomId)
        .map(this::convertToRoomInfo)
        .orElseThrow(() -> new HotelServiceException(HotelErrorResponse.NOT_FOUND));
  }

  private RoomInfoResponse convertToRoomInfo(Room room) {
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
