package com.incubator.dbs.reservationservice.controller;

import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequestDTO;
import com.incubator.dbs.reservationservice.service.ReservationService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Bulkhead(name = "reservations", type = Type.SEMAPHORE)
public class ReservationsController implements ReservationOperations {

  private final ReservationService reservationService;

  public ReservationsController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @Override
  @ResponseStatus(value = HttpStatus.CREATED)
  public ReservationInfoResponseDTO create(@Valid CreateReservationRequestDTO request) {
    return reservationService.create(request);
  }

  @Override
  public ReservationInfoResponseDTO update(String id, @Valid UpdateReservationRequestDTO request) {
    return reservationService.update(id, request);
  }

  @Override
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(String id) {
    reservationService.delete(id);
  }

  @Override
  public List<ReservationInfoResponseDTO> getByUser(String id) {
    return reservationService.getByUser(id);
  }

  @Override
  public List<ReservationInfoResponseDTO> get(Instant from, Instant to) {
    return reservationService.get(from, to);
  }
}
