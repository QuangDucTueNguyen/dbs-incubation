package com.incubator.dbs.hotelservice.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHotelRequest {
  @NotBlank
  private String name;
  private String hotline;
  @NotBlank
  private String address;
}
