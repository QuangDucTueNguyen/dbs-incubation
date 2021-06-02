package com.incubator.dbs.authenticateservice.model.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "user_credential", schema = "public")
public class CredentialEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String username;
  private UUID guestId;
  private String password;
  @OneToMany(mappedBy = "userCredential", targetEntity = UserRoleEntity.class)
  private List<UserRoleEntity> userRoleEntities;
}
