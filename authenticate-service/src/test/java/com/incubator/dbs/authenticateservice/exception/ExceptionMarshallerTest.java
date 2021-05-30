package com.incubator.dbs.authenticateservice.exception;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExceptionMarshallerTest {

  private ObjectMapper mapper;
  private Tracer tracer;
  private ExceptionMarshaller exceptionMarshaller;
  private Span span;
  private static final Long TRACE_ID_VALUE = 100000L;
  private static final Long SPAN_ID_VALUE = Long.MAX_VALUE - 1;
  private final String ERROR_CODE = "errorCode";
  private final String TRACE_ID = "traceId";

  public ExceptionMarshallerTest() {
    this.mapper = new ObjectMapper();
    this.tracer = Mockito.mock(Tracer.class);
    this.span = Mockito.mock(Span.class);
    exceptionMarshaller = new ExceptionMarshaller(mapper, tracer);
  }

  @Test
  public void toJsonNode_shouldWork() {
    var currentSpan = this.span;
    var tracerContext = TraceContext.newBuilder().traceId(TRACE_ID_VALUE).spanId(SPAN_ID_VALUE).build();
    Mockito.when(tracer.currentSpan()).thenReturn(currentSpan);
    Mockito.when(tracer.currentSpan().context()).thenReturn(tracerContext);
    var result = exceptionMarshaller.toJsonNode(AuthenticationErrorResponse.UNHANDLED_EXCEPTION, Optional.empty());
    Assertions.assertEquals(AuthenticationErrorResponse.UNHANDLED_EXCEPTION.getErrorCode(),
        result.findValue(ERROR_CODE).textValue());
    Assertions.assertEquals(tracerContext.traceIdString(), result.findValue(TRACE_ID).textValue());
  }
}
