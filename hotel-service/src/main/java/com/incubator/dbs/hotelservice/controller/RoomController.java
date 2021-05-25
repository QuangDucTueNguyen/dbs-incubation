package com.incubator.dbs.hotelservice.controller;

import com.incubator.dbs.hotelservice.model.dto.CreateRoomRequest;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomTypeResponse;
import com.incubator.dbs.hotelservice.model.dto.UpdateRoomRequest;
import com.incubator.dbs.hotelservice.service.RoomService;
import java.time.Instant;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController implements RoomOperations {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @Override
  public List<RoomTypeResponse> getAllRoomType() {
    return roomService.getAllRoomType();
  }

  @Override
  public RoomInfoResponse createRoom(CreateRoomRequest createRoomRequest) {
    return roomService.createRoom(createRoomRequest);
  }

  @Override
  public RoomInfoResponse updateRoom(Integer id, UpdateRoomRequest updateRoomRequest) {
    return roomService.updateRoom(id, updateRoomRequest);
  }

  @Override
  public List<RoomTypeResponse> getAllRoomInArrangeTime(Instant from, Instant to) {
    return roomService.getAllRoomInArrangeTime(from, to);
  }
}
