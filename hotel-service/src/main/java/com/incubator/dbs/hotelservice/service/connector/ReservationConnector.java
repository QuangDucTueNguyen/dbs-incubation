package com.incubator.dbs.hotelservice.service.connector;

import com.incubator.dbs.hotelservice.model.dto.ReservationResponse;
import java.time.Instant;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * To communicate with reservation service
 */
@FeignClient(value = "reservationService", url = "${reservation.host}")
public interface ReservationConnector {

  /**
   * Get all reservation of hotel in specific time
   *
   * @param from
   * @param to
   * @returnS
   */
  @GetMapping(value = "${reservation.getReservation}")
  List<ReservationResponse> getAllReservationInTime(@RequestParam Instant from, @RequestParam Instant to);
}
