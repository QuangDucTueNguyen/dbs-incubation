package com.incubator.dbs.reservationservice.model.entity;

import com.incubator.dbs.reservationservice.model.constant.ReservationStatus;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation", schema = "public")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;
  private UUID userId;
  private Integer hotelId;
  private Integer roomTypeId;
  @Enumerated(EnumType.STRING)
  private ReservationStatus status;
  private Double total;
  private Integer numberRooms;
  private Instant createdTime;
  private Instant updateTime;
  private Instant fromDate;
  private Instant toDate;
}
