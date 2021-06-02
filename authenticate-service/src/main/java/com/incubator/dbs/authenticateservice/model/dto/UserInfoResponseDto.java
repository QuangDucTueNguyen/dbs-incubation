package com.incubator.dbs.authenticateservice.model.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

  private UUID id;
  private String name;
  private String address;
  private String phoneNumber;
}
