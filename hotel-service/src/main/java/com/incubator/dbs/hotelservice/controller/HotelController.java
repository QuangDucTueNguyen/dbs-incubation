package com.incubator.dbs.hotelservice.controller;

import com.incubator.dbs.hotelservice.model.dto.CreateHotelRequest;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.service.HotelService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelController implements HotelOperations {

  private final HotelService hotelService;

  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @Override
  public RoomInfoResponse getRoom(@PathVariable Integer hotelId, @PathVariable Integer roomId) {
    return hotelService.getRoom(hotelId, roomId);
  }

  @Override
  public CreateHotelResponse createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest) {
    return hotelService.createHotel(createHotelRequest);
  }
}
