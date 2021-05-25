package com.incubator.dbs.hotelservice.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeResponse {

  private Integer id;
  private String type;
  private Double price;
  private Integer numberPersonPerRoom;
  private Integer numberAvailable;
  private List<CreateHotelResponse> hotelInfo;
}
