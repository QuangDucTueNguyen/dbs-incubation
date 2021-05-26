package com.incubator.dbs.hotelservice.model.entity;

import com.incubator.dbs.hotelservice.model.constant.RoomStatus;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "room", schema = "public")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Enumerated(EnumType.STRING)
  private RoomStatus status;
  private String name;
  private String description;
  @ManyToOne(targetEntity = com.incubator.dbs.hotelservice.model.entity.RoomType.class)
  @JoinColumn(name = "room_type_id")
  private RoomType roomType;
  @ManyToOne(targetEntity = com.incubator.dbs.hotelservice.model.entity.Hotel.class)
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

}
