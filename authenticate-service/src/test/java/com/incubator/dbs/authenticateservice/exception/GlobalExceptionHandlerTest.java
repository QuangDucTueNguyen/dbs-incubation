package com.incubator.dbs.authenticateservice.exception;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

  private ExceptionMarshaller exceptionMarshaller;
  private GlobalExceptionHandler globalExceptionHandler;
  private final String ERROR_CODE = "errorCode";
  private ObjectMapper mapper;
  private Tracer tracer;
  private static final Long TRACE_ID_VALUE = 100000L;
  private static final Long SPAN_ID_VALUE = Long.MAX_VALUE - 1;
  private static String MESSAGE = "MESSAGE";
  private static final String OBJECT_NAME = "OBJECT_NAME";

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
    Assertions.assertEquals(AuthenticationErrorResponse.UNHANDLED_EXCEPTION.getErrorCode(),
        result.getBody().findValue(ERROR_CODE).textValue());
  }

  @Test
  public void handleGuestException_shouldWord() {
    var result = globalExceptionHandler
        .handleGuestException(new AuthenticationServiceException(AuthenticationErrorResponse.NOT_AUTHORIZED));
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    Assertions.assertEquals(AuthenticationErrorResponse.NOT_AUTHORIZED.getErrorCode(),
        result.getBody().findValue(ERROR_CODE).textValue());
  }

  @Test
  void handleMethodArgumentNotValidException_shouldWord() {
    var methodArgumentNotValidException = Mockito.mock(MethodArgumentNotValidException.class);
    var bindingResult = Mockito.mock(BindingResult.class);
    var objectError = new ObjectError(OBJECT_NAME, "Invalid object.");
    Mockito.when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    Mockito.when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(objectError));
    var result = globalExceptionHandler
        .handleMethodArgumentNotValidException(methodArgumentNotValidException);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    Assertions.assertEquals(AuthenticationErrorResponse.INVALID_VALUE.getErrorCode(),
        result.getBody().findValue(ERROR_CODE).textValue());
  }
}
