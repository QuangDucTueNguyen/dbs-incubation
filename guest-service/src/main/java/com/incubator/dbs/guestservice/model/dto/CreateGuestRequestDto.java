package com.incubator.dbs.guestservice.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGuestRequestDto {
  @NotBlank
  private String name;
  private String address;
  private String phoneNumber;
}
