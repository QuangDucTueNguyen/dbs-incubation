package com.incubator.dbs.authenticateservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponseDto {
  private String defaultPassword;
}
