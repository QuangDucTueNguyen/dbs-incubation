package com.incubator.dbs.hotelservice.repository;

import com.incubator.dbs.hotelservice.model.entity.RoomType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends CrudRepository<RoomType, Integer> {
  Optional<List<RoomType>> findAllByName(String name);
}
