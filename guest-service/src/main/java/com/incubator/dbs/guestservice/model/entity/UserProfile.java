package com.incubator.dbs.guestservice.model.entity;

import java.math.BigInteger;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "guest_profile", schema = "public")
public class UserProfile {

  @Id
  @GeneratedValue
  private UUID id;
  private String username;
  private String name;
  private String address;
  private BigInteger creditCard;
  private String phoneNumber;
  @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
  private UserPassword password;
}
