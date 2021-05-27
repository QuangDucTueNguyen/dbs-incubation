package com.incubator.dbs.guestservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponseDto {
  private String defaultPassword;
}
