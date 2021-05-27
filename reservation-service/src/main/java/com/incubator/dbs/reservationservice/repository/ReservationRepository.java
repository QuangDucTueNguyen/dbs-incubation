package com.incubator.dbs.reservationservice.repository;

import com.incubator.dbs.reservationservice.model.entity.Reservation;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {

  List<Reservation> findAllByUserId(UUID userId);

  List<Reservation> findAllByFromDateAfterAndToDateBefore(Instant from, Instant to);

}
