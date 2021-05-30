package com.incubator.dbs.authenticateservice.exception;

import lombok.Data;

@Data
public class AuthenticationServiceException extends RuntimeException {

  private final AuthenticationErrorResponse guestErrorResponse;

  public AuthenticationServiceException(AuthenticationErrorResponse guestErrorResponse) {
    this.guestErrorResponse = guestErrorResponse;
  }
}
