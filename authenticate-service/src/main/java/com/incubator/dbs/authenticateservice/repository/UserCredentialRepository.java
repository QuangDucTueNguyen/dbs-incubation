package com.incubator.dbs.authenticateservice.repository;

import com.incubator.dbs.authenticateservice.model.entity.CredentialEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends CrudRepository<CredentialEntity, String> {

  Optional<CredentialEntity> findOneByUsernameAndPassword(String username, String password);
  Optional<CredentialEntity> findOneByUsername(String username);

}
