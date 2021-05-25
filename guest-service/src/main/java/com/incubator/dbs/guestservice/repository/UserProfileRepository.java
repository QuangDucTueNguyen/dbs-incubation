package com.incubator.dbs.guestservice.repository;

import com.incubator.dbs.guestservice.model.entity.UserProfile;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, String> {

  Optional<Boolean> existsByUsername(String userName);
  Optional<UserProfile> findOneByUsername(String userName);
}
