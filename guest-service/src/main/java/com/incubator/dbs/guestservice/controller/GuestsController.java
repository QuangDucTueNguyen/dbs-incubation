package com.incubator.dbs.guestservice.controller;

import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.service.UserService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Bulkhead(name = "guests", type = Type.SEMAPHORE)
public class GuestsController implements GuestsOperation {

  @Autowired
  private final UserService userService;

  public GuestsController(UserService userService) {
    this.userService = userService;
  }

  @Override
  @ResponseStatus(HttpStatus.CREATED)
  public GuestInfoResponseDto signUp(@Valid @RequestBody CreateGuestRequestDto request) {
    return userService.create(request);
  }

  @Override
  public GuestInfoResponseDto get(UUID id) {
    return userService.get(id);
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(UUID id) {
    userService.delete(id);
  }
}
