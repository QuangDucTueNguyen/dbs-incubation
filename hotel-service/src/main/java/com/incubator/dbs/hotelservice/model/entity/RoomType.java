package com.incubator.dbs.hotelservice.model.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "room_type", schema = "public")
public class RoomType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Boolean isVirtual;
  private BigDecimal price;
  private String name;
  private Integer numberPeople;
  @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL,
      targetEntity = com.incubator.dbs.hotelservice.model.entity.Room.class)
  private List<Room> rooms;
}
