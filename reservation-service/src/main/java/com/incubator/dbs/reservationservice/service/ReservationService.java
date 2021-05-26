package com.incubator.dbs.reservationservice.service;

import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequest;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponse;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequest;
import java.time.Instant;
import java.util.List;

public interface ReservationService {

  ReservationInfoResponse create(CreateReservationRequest request);

  ReservationInfoResponse update(String id, UpdateReservationRequest request);

  void delete(String id);

  List<ReservationInfoResponse> getByUser(String id);

  List<ReservationInfoResponse> get(Instant from, Instant to);
}
