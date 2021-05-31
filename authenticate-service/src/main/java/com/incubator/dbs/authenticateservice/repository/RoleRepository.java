package com.incubator.dbs.authenticateservice.repository;

import com.incubator.dbs.authenticateservice.model.constant.RoleName;
import com.incubator.dbs.authenticateservice.model.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
  Optional<RoleEntity> findByName(RoleName name);
}
