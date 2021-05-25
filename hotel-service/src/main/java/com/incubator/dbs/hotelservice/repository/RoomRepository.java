package com.incubator.dbs.hotelservice.repository;

import com.incubator.dbs.hotelservice.model.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

}
