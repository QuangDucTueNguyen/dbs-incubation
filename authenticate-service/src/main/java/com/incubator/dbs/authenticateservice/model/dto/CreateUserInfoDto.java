package com.incubator.dbs.authenticateservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserInfoDto {

  private String name;
  private String address;
  private String phoneNumber;
}
