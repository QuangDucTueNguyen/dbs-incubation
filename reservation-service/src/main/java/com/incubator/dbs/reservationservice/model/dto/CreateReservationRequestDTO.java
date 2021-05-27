package com.incubator.dbs.reservationservice.model.dto;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationRequestDTO {
  @NotNull
  private Integer hotelId;
  @NotNull
  private Integer roomTypeId;
  @NotNull
  private Integer numberRooms;
  @NotNull
  private Instant from;
  @NotNull
  private Instant to;
  private String userId;
}
