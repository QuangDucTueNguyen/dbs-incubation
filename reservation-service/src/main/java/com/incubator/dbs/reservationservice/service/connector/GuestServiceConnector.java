package com.incubator.dbs.reservationservice.service.connector;

import com.incubator.dbs.reservationservice.model.dto.UserInfoResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "guestService", url = "${guestService.host}")
@CircuitBreaker(name = "reservations")
public interface GuestServiceConnector {

  @GetMapping(value = "${guestService.getUserInfo}")
  Optional<UserInfoResponseDTO> getUserInfo(@PathVariable String id);
}
