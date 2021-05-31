package com.incubator.dbs.hotelservice.controller;

import com.incubator.dbs.hotelservice.model.dto.CreateHotelRequest;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.service.HotelService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CircuitBreaker(name = "hotels")
@Retry(name = "hotels")
@RateLimiter(name = "hotels")
@TimeLimiter(name = "hotels")
@Bulkhead(name = "hotels")
public class HotelsController implements HotelOperations {

  private final HotelService hotelService;

  public HotelsController(HotelService hotelService) {
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
