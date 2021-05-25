package com.incubator.dbs.hotelservice.model.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
  @NotNull
  private Integer typeId;
  @NotNull
  private Integer hotelId;
  private String name;
  private String description;
}
