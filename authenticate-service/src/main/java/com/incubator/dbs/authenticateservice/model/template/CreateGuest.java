package com.incubator.dbs.authenticateservice.model.template;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGuest {

  private UUID id;
  private String name;
  private String status;
}
