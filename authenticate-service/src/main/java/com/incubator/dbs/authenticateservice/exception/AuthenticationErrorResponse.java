package com.incubator.dbs.authenticateservice.exception;

import org.springframework.http.HttpStatus;

public enum AuthenticationErrorResponse {
  UNHANDLED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "4000", "Unhandled exception."),
  NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "4001", "Not authorized."),
  INVALID_VALUE(HttpStatus.BAD_REQUEST, "4002", "Invalid parameter."),
  USER_EXIST(HttpStatus.BAD_REQUEST, "4003", "User is existing.");

  private HttpStatus httpStatus;
  private String errorCode;
  private String errorMessage;

  AuthenticationErrorResponse(HttpStatus httpStatus, String errorCode, String errorMessage) {
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
