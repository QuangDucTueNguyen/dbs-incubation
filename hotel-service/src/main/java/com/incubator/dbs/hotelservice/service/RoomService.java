package com.incubator.dbs.hotelservice.service;

import com.incubator.dbs.hotelservice.model.dto.CreateRoomRequest;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomTypeResponse;
import com.incubator.dbs.hotelservice.model.dto.UpdateRoomRequest;
import java.time.Instant;
import java.util.List;

public interface RoomService {

  List<RoomTypeResponse> getAllRoomType();

  RoomInfoResponse createRoom(CreateRoomRequest createRoomRequest);

  RoomInfoResponse updateRoom(Integer id, UpdateRoomRequest updateRoomRequest);

  List<RoomTypeResponse> getAllRoomInArrangeTime(Instant from, Instant to);
}
