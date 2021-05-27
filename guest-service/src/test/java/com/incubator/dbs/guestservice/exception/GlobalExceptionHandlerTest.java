package com.incubator.dbs.guestservice.exception;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

  private final long traceId = 5260289265077670476L;
  private final long spanId = new Random().nextLong();
  private ExceptionMarshaller exceptionMarshaller;
  private GlobalExceptionHandler globalExceptionHandler;
  private final String ERROR_CODE = "errorCode";
  private final String ERROR_MESSAGE = "errorMessage";
  private final String TRACE_ID = "traceId";
  private ObjectMapper mapper;
  private Tracer tracer;
  private static final Long TRACE_ID_VALUE = 100000L;
  private static final Long SPAN_ID_VALUE = Long.MAX_VALUE - 1;
  private static String MESSAGE = "MESSAGE";

  public GlobalExceptionHandlerTest() {
    mapper = new ObjectMapper();
    this.tracer = Mockito.mock(Tracer.class);
    exceptionMarshaller = new ExceptionMarshaller(mapper, tracer);
    globalExceptionHandler = new GlobalExceptionHandler(exceptionMarshaller);
  }

  @BeforeEach
  public void setup() {
    var tracerContext = TraceContext.newBuilder().traceId(TRACE_ID_VALUE).spanId(SPAN_ID_VALUE).build();
    var currentSpan = Mockito.mock(Span.class);
    Mockito.when(tracer.currentSpan()).thenReturn(currentSpan);
    Mockito.when(tracer.currentSpan().context()).thenReturn(tracerContext);
  }

  @Test
  public void handleException_shouldWord() {
    var result = globalExceptionHandler.handleException(new Exception(MESSAGE));
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    Assertions.assertEquals(GuestErrorResponse.UNHANDLED_EXCEPTION.getErrorCode(),
        result.getBody().findValue(ERROR_CODE).textValue());
  }

  @Test
  public void handleGuestException_shouldWord() {
    var result = globalExceptionHandler
        .handleGuestException(new GuestServiceException(GuestErrorResponse.USER_EXISTING));
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    Assertions.assertEquals(GuestErrorResponse.USER_EXISTING.getErrorCode(),
        result.getBody().findValue(ERROR_CODE).textValue());
  }
}
