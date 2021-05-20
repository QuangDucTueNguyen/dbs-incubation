package com.incubator.dbs.guestservice.exception;

import lombok.Data;

@Data
public class GuestServiceException extends RuntimeException {

  private final GuestErrorResponse guestErrorResponse;

  public GuestServiceException(GuestErrorResponse guestErrorResponse) {
    this.guestErrorResponse = guestErrorResponse;
  }
}
