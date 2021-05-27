package com.incubator.dbs.guestservice.repository;

import com.incubator.dbs.guestservice.model.entity.UserPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends CrudRepository<UserPassword, Long> {

}
