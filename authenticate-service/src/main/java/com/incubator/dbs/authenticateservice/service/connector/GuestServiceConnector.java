package com.incubator.dbs.authenticateservice.service.connector;

import com.incubator.dbs.authenticateservice.model.dto.CreateUserInfoDto;
import com.incubator.dbs.authenticateservice.model.dto.UserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "guestService", url = "${guestService.host}")
public interface GuestServiceConnector {

  @PostMapping(value = "${guestService.createUserProfile}")
  UserInfoResponseDto create(@RequestBody CreateUserInfoDto request);
}
