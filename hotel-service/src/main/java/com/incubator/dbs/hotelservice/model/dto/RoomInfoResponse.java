package com.incubator.dbs.hotelservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.incubator.dbs.hotelservice.model.constant.RoomStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class RoomInfoResponse {

  private Integer id;
  private Integer hotelId;
  private String hotelName;
  private String name;
  private String type;
  private Integer typeId;
  private Integer numberAvailable;
  private BigDecimal price;
  private String description;
  private Integer numberPersonPerRoom;
  private RoomStatus status;
}
