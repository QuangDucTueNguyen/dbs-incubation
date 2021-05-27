package com.incubator.dbs.reservationservice.model.dto;

import com.incubator.dbs.reservationservice.model.constant.ReservationStatus;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReservationInfoResponseDTO {

  private UUID id;
  private Integer hotelId;
  private Integer roomTypeId;
  private Integer numberRooms;
  private Double total;
  private Instant from;
  private Instant to;
  private Instant createdTime;
  private ReservationStatus status;
}
