package com.incubator.dbs.guestservice.model.template.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubator.dbs.guestservice.model.template.CreateGuest;
import org.apache.kafka.common.serialization.Deserializer;

public class CreateGuestDeserializer implements Deserializer<CreateGuest> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public CreateGuest deserialize(String s, byte[] data) {
    try {
      return objectMapper.readValue(data, CreateGuest.class);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
