package com.incubator.dbs.guestservice.exception;

import org.springframework.http.HttpStatus;

public enum GuestErrorResponse {
  UNHANDLED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "1000", "Unhandled exception."),
  NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "1001", "Not authorized."),
  USER_EXISTING(HttpStatus.CONFLICT, "1002", "User name is existing."),
  NOT_FOUND(HttpStatus.NOT_FOUND, "1003", "Not found.");

  private HttpStatus httpStatus;
  private String errorCode;
  private String errorMessage;

  GuestErrorResponse(HttpStatus httpStatus, String errorCode, String errorMessage) {
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
