package com.incubator.dbs.authenticateservice.model.template.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubator.dbs.authenticateservice.model.template.CreateGuest;
import org.apache.kafka.common.serialization.Serializer;

public class CreateGuestSerializer implements Serializer<CreateGuest> {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public byte[] serialize(String s, CreateGuest createGuest) {
    try {
      return objectMapper.writeValueAsString(createGuest).getBytes();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
