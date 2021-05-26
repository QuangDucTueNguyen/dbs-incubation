package com.incubator.dbs.reservationservice.repository;

import com.incubator.dbs.reservationservice.model.entity.Reservation;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {

  List<Reservation> findAllByUserId(UUID userId);
  @Query(value = "select * from reservation r where r.fromDate <= :from AND r.toDate >= :to", nativeQuery = true)
  List<Reservation> findAllByInArrangeTime(Instant from, Instant to);

}
