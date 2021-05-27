package com.incubator.dbs.reservationservice.service;

import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequestDTO;
import java.time.Instant;
import java.util.List;

public interface ReservationService {

  ReservationInfoResponseDTO create(CreateReservationRequestDTO request);

  ReservationInfoResponseDTO update(String id, UpdateReservationRequestDTO request);

  void delete(String id);

  List<ReservationInfoResponseDTO> getByUser(String id);

  List<ReservationInfoResponseDTO> get(Instant from, Instant to);
}
