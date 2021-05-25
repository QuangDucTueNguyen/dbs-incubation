package com.incubator.dbs.hotelservice.model.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

  private Integer hotelId;
  private Instant from;
  private Instant to;
  private Integer numberRooms;
  private Double total;
  private Integer roomTypeId;
}
