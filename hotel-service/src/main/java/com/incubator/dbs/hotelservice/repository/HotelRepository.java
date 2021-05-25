package com.incubator.dbs.hotelservice.repository;

import com.incubator.dbs.hotelservice.model.entity.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Integer> {

}
