package com.incubator.dbs.hotelservice.service;

import com.incubator.dbs.hotelservice.model.dto.CreateHotelRequest;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;

public interface HotelService {

  CreateHotelResponse createHotel(CreateHotelRequest createHotelRequest);

  RoomInfoResponse getRoom(Integer hotelId, Integer roomId);
}
