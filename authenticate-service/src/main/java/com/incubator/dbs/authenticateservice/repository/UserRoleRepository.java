package com.incubator.dbs.authenticateservice.repository;

import com.incubator.dbs.authenticateservice.model.entity.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Integer> {

}
