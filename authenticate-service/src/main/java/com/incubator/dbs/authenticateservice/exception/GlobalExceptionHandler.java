package com.incubator.dbs.authenticateservice.exception;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private final ExceptionMarshaller exceptionMarshaller;

  public GlobalExceptionHandler(ExceptionMarshaller exceptionMarshaller) {
    this.exceptionMarshaller = exceptionMarshaller;
  }

  /**
   * Handle global exception
   *
   * @param e
   * @return
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<JsonNode> handleException(Exception e) {
    log.error("An exception occur.", e);
    return new ResponseEntity<>(
        exceptionMarshaller.toJsonNode(AuthenticationErrorResponse.UNHANDLED_EXCEPTION, Optional.empty()),
        new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle GuestServiceException
   *
   * @param ex
   * @return
   */
  @ExceptionHandler(value = AuthenticationServiceException.class)
  public ResponseEntity<JsonNode> handleGuestException(AuthenticationServiceException ex) {
    log.error("An GuestServiceException occur.", ex);
    return new ResponseEntity<>(
        exceptionMarshaller.toJsonNode(ex.getGuestErrorResponse(), Optional.empty()),
        new HttpHeaders(),
        ex.getGuestErrorResponse().getHttpStatus());
  }

  /**
   * Handle MethodArgumentNotValidException
   *
   * @param ex
   * @return
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<JsonNode> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("An MethodArgumentNotValidException occur.", ex);
    return new ResponseEntity<>(
        exceptionMarshaller.toJsonNode(
            AuthenticationErrorResponse.INVALID_VALUE,
            ex.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)),
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }
}
