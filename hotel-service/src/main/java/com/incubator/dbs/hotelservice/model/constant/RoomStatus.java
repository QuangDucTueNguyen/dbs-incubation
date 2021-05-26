package com.incubator.dbs.hotelservice.model.constant;

import java.util.Optional;

public enum RoomStatus {
  PLANNED("PLANNED"),
  CLEAN("CLEAN"),
  ACTIVE("ACTIVE");

  private String value;

  RoomStatus(String value) {
    this.value = value;
  }

  public static Optional<RoomStatus> fromValue(String value) {
    for (RoomStatus object : values()) {
      if (object.getValue().equals(value)) {
        return Optional.of(object);
      }
    }
    return Optional.empty();
  }

  public String getValue() {
    return value;
  }
}
