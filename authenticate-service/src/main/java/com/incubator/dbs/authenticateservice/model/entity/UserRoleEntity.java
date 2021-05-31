package com.incubator.dbs.authenticateservice.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@Table(name = "user_roles", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_credential_id")
  private CredentialEntity userCredential;
  @ManyToOne
  @JoinColumn(name = "role_id")
  private RoleEntity role;
}
