package com.incubator.dbs.authenticateservice.service.connector;

import com.incubator.dbs.authenticateservice.model.dto.CreateUserInfoDto;
import com.incubator.dbs.authenticateservice.model.dto.UserInfoResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "guestService", url = "${guestService.host}")
@CircuitBreaker(name = "auth")
public interface GuestServiceConnector {

  @PostMapping(value = "${guestService.createUserProfile}")
  UserInfoResponseDto create(@RequestBody CreateUserInfoDto request);
}
