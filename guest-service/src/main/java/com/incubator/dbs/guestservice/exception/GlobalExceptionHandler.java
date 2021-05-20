package com.incubator.dbs.guestservice.exception;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  private final ExceptionMarshaller exceptionMarshaller;

  public GlobalExceptionHandler(ExceptionMarshaller exceptionMarshaller) {
    this.exceptionMarshaller = exceptionMarshaller;
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<JsonNode> handleException(Exception e) {
    log.error("An exception occur.", e);
    return new ResponseEntity<>(
        exceptionMarshaller.toJsonNode(GuestErrorResponse.UNHANDLED_EXCEPTION, Optional.empty()),
        new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = GuestServiceException.class)
  public ResponseEntity<Object> handleGuestException(GuestServiceException ex) {
    log.error("An GuestServiceException occur.", ex);
    return new ResponseEntity<>(
        exceptionMarshaller.toJsonNode(ex.getGuestErrorResponse(), Optional.empty()),
        new HttpHeaders(),
        ex.getGuestErrorResponse().getHttpStatus());
  }
}
