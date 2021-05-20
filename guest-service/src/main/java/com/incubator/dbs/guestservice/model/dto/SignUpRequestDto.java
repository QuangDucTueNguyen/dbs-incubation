package com.incubator.dbs.guestservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDto {
  private String username;
  private String name;
  private String address;
  private String phoneNumber;
}
