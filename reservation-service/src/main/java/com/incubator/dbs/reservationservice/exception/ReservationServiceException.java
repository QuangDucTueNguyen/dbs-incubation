package com.incubator.dbs.reservationservice.exception;

import lombok.Data;

@Data
public class ReservationServiceException extends RuntimeException{

  private final ReservationErrorResponse reservationErrorResponse;

  public ReservationServiceException(ReservationErrorResponse reservationErrorResponse) {
    this.reservationErrorResponse = reservationErrorResponse;
  }

}
