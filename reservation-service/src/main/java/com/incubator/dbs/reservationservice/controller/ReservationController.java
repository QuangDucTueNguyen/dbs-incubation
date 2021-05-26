package com.incubator.dbs.reservationservice.controller;

import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequest;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponse;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequest;
import com.incubator.dbs.reservationservice.service.ReservationService;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController implements ReservationOperations {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Override
  @ResponseStatus(value = HttpStatus.CREATED)
  public ReservationInfoResponse create(@Valid CreateReservationRequest request) {
    return reservationService.create(request);
  }

  @Override
  public ReservationInfoResponse update(String id, @Valid UpdateReservationRequest request) {
    return reservationService.update(id, request);
  }

  @Override
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(String id) {
    reservationService.delete(id);
  }

  @Override
  public List<ReservationInfoResponse> getByUser(String id) {
    return reservationService.getByUser(id);
  }

  @Override
  public List<ReservationInfoResponse> get(Instant from, Instant to) {
    return reservationService.get(from, to);
  }
}
