package com.incubator.dbs.guestservice.repository;

import com.incubator.dbs.guestservice.model.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, String> {

}
