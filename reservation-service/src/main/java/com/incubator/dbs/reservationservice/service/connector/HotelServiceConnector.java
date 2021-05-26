package com.incubator.dbs.reservationservice.service.connector;

import com.incubator.dbs.reservationservice.model.dto.RoomTypeResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "hotelService", url = "${hotelService.host}")
public interface HotelServiceConnector {

  @GetMapping(value = "${hotelService.getAllRoomType}")
  List<RoomTypeResponse> getAllRoomType();
}
