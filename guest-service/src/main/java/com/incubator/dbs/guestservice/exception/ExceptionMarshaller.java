package com.incubator.dbs.guestservice.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import brave.Tracer;

@Component
public class ExceptionMarshaller {

  private final String ERROR_CODE = "errorCode";
  private final String ERROR_MESSAGE = "errorMessage";
  private final String TRACE_ID = "traceId";
  private final ObjectMapper mapper;
  private final Tracer tracer;

  public ExceptionMarshaller(ObjectMapper mapper, Tracer tracer) {
    this.mapper = mapper;
    this.tracer = tracer;
  }

  public JsonNode toJsonNode(GuestErrorResponse errorDetail, Optional<String> extra) {

    ObjectNode rootNode = this.mapper.createObjectNode();
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
