package com.incubator.dbs.reservationservice.service.connector;

import com.incubator.dbs.reservationservice.model.dto.UserInfoResponse;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "guestService", url = "${guestService.host}")
public interface GuestServiceConnector {

  @GetMapping(value = "${guestService.getUserInfo}")
  Optional<UserInfoResponse> getUserInfo(@PathVariable String id);
}
