package com.incubator.dbs.guestservice.model.template;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGuest {

  private UUID id;
  private String name;
  private String status;
}
