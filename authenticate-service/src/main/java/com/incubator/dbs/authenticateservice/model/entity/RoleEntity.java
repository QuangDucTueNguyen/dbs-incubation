package com.incubator.dbs.authenticateservice.model.entity;

import com.incubator.dbs.authenticateservice.model.constant.RoleName;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "roles", schema = "public")
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Enumerated(EnumType.STRING)
  private RoleName name;
  private String description;
  @OneToMany(mappedBy = "role", targetEntity = UserRoleEntity.class)
  private List<UserRoleEntity> userRoleEntities;
}
