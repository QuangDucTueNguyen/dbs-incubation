package com.incubator.dbs.hotelservice.exception;

import org.springframework.http.HttpStatus;

public enum HotelErrorResponse {

  UNHANDLED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "2000", "Unhandled exception."),
  INVALID_VALUE(HttpStatus.BAD_REQUEST, "2001", "Invalid value."),
  NOT_FOUND(HttpStatus.NOT_FOUND, "2002", "Not found.");

  private HttpStatus httpStatus;
  private String errorCode;
  private String errorMessage;

  HotelErrorResponse(HttpStatus httpStatus, String errorCode, String errorMessage) {
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
