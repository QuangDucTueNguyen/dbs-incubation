package com.incubator.dbs.hotelservice.exception;

import lombok.Data;

@Data
public class HotelServiceException extends RuntimeException {

  private final HotelErrorResponse hotelErrorResponse;

  public HotelServiceException(HotelErrorResponse hotelErrorResponse) {
    this.hotelErrorResponse = hotelErrorResponse;
  }
}
