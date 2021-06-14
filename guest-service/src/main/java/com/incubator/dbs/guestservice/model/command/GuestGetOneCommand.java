package com.incubator.dbs.guestservice.model.command;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@Builder
public class GuestGetOneCommand {

  @TargetAggregateIdentifier
  private UUID id;
}
