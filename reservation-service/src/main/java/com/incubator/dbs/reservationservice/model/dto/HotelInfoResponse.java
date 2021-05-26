package com.incubator.dbs.reservationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelInfoResponse {

  private Integer id;
  private String name;
  private String hotline;
  private String address;
}
