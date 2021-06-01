package com.incubator.dbs.hotelservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class RoomTypeResponse {

  private Integer id;
  private String type;
  private BigDecimal price;
  private Integer numberPersonPerRoom;
  private Integer numberAvailable;
  private List<CreateHotelResponse> hotelInfo;
}
