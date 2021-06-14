package com.incubator.dbs.guestservice.model.entity;

import java.math.BigInteger;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "guest_profile", schema = "public")
public class UserProfile {

  @Id
  private UUID id;
  private String name;
  private String address;
  private BigInteger creditCard;
  private String phoneNumber;
}
