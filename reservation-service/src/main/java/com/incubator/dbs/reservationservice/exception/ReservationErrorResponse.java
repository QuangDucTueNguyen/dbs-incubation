package com.incubator.dbs.reservationservice.exception;

import org.springframework.http.HttpStatus;

public enum ReservationErrorResponse {
  UNHANDLED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "3000", "Unhandled exception."),
  INVALID_VALUE(HttpStatus.BAD_REQUEST, "3001", "Invalid value."),
  NOT_FOUND(HttpStatus.NOT_FOUND, "3002", "Not found.");

  private HttpStatus httpStatus;
  private String errorCode;
  private String errorMessage;

  ReservationErrorResponse(HttpStatus httpStatus, String errorCode, String errorMessage) {
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
