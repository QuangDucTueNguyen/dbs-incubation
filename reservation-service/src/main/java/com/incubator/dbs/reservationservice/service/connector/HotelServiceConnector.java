package com.incubator.dbs.reservationservice.service.connector;

import com.incubator.dbs.reservationservice.model.dto.RoomTypeResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "hotelService", url = "${hotelService.host}")
@CircuitBreaker(name = "reservations")
public interface HotelServiceConnector {

  @GetMapping(value = "${hotelService.getAllRoomType}")
  List<RoomTypeResponseDTO> getAllRoomType();
}
