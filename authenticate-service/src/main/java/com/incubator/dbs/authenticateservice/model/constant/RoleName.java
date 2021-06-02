package com.incubator.dbs.authenticateservice.model.constant;

import java.util.Optional;

public enum RoleName {
  ROOT("ROOT"),
  ADMIN("ADMIN"),
  USER("USER");

  private String value;

  RoleName(String value) {
    this.value = value;
  }

  public static Optional<RoleName> fromValue(String value) {
    for (RoleName object : values()) {
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
