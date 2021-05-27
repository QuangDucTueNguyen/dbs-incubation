package com.incubator.dbs.reservationservice.model.constant;

import java.util.Optional;

public enum ReservationStatus {
  PENDING("PENDING"),
  CANCEL("CANCEL"),
  PAID("PAID");

  private String value;

  ReservationStatus(String value) {
    this.value = value;
  }

  public static Optional<ReservationStatus> fromValue(String value) {
    for (ReservationStatus object : values()) {
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
