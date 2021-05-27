package com.incubator.dbs.reservationservice.exception;

import brave.Tracer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMarshaller {

  private static final String ERROR_CODE = "errorCode";
  private static final String ERROR_MESSAGE = "errorMessage";
  private static final String TRACE_ID = "traceId";
  private final ObjectMapper mapper;
  private final Tracer tracer;

  public ExceptionMarshaller(ObjectMapper mapper, Tracer tracer) {
    this.mapper = mapper;
    this.tracer = tracer;
  }

  public JsonNode toJsonNode(ReservationErrorResponse errorDetail, Optional<String> extra) {

    var rootNode = this.mapper.createObjectNode();
    rootNode.put(ERROR_CODE, errorDetail.getErrorCode());
    rootNode.put(ERROR_MESSAGE, errorDetail.getErrorMessage() + extra.orElse(StringUtils.EMPTY));
    rootNode.put(TRACE_ID, getTraceId());
    return rootNode;
  }

  private String getTraceId() {
    if (Objects.isNull(tracer.currentSpan())
        || Objects.isNull(tracer.currentSpan().context())) {
      return StringUtils.EMPTY;
    }

    return tracer.currentSpan().context().traceIdString();
  }
}
