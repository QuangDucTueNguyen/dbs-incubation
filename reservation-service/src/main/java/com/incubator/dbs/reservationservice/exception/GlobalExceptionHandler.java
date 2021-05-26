package com.incubator.dbs.reservationservice.exception;

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
        exceptionMarshaller.toJsonNode(ReservationErrorResponse.UNHANDLED_EXCEPTION, Optional.empty()),
        new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle handleReservationException
   *
   * @param ex
   * @return
   */
  @ExceptionHandler(value = ReservationServiceException.class)
  public ResponseEntity<JsonNode> handleReservationException(ReservationServiceException ex) {
    log.error("An HotelServiceException occur.", ex);
    return new ResponseEntity<>(
        exceptionMarshaller.toJsonNode(ex.getReservationErrorResponse(), Optional.empty()),
        new HttpHeaders(),
        ex.getReservationErrorResponse().getHttpStatus());
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
            ReservationErrorResponse.INVALID_VALUE,
            ex.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)),
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }
}
