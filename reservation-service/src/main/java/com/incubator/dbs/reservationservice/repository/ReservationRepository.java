package com.incubator.dbs.reservationservice.repository;

import com.incubator.dbs.reservationservice.model.entity.Reservation;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, UUID> {

  List<Reservation> findAllByUserId(UUID userId);

  List<Reservation> findAllByFromDateBeforeAndToDateAfter(Instant from, Instant to);

}
