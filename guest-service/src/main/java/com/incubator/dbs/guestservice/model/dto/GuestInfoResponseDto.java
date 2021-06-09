package com.incubator.dbs.guestservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigInteger;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
public class GuestInfoResponseDto {

  private UUID id;
  private String name;
  private String address;
  private BigInteger creditCard;
  private String phoneNumber;
}
