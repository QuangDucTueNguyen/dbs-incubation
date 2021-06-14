package com.incubator.dbs.guestservice.handler.command;

import com.incubator.dbs.guestservice.model.command.GuestAddCommand;
import com.incubator.dbs.guestservice.model.command.GuestRemoveCommand;
import com.incubator.dbs.guestservice.model.event.GuestAddEvent;
import com.incubator.dbs.guestservice.model.event.GuestRemoveEvent;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuestCommandHandler {

  @AggregateIdentifier
  private UUID id;
  private String name;
  private String address;
  private String phoneNumber;
  private Boolean isActive;

  @CommandHandler
  public GuestCommandHandler(GuestAddCommand guestAddCommand){
    log.info("handle create guest command: {}", guestAddCommand);
    AggregateLifecycle.apply(GuestAddEvent.builder()
        .id(guestAddCommand.getId())
        .address(guestAddCommand.getAddress())
        .name(guestAddCommand.getName())
        .phoneNumber(guestAddCommand.getPhoneNumber())
        .build());
  }

  @CommandHandler
  public void handle(GuestRemoveCommand guestRemoveCommand) {
    log.info("handle remove guest command: {}", guestRemoveCommand);
    AggregateLifecycle.apply(GuestRemoveEvent.builder()
        .id(guestRemoveCommand.getId())
        .build());
  }

  @EventSourcingHandler
  public void on(GuestAddEvent guestAddEvent) {
    log.info("Event sourcing on add guest event: {}", guestAddEvent);
    id = guestAddEvent.getId();
    name = guestAddEvent.getName();
    phoneNumber = guestAddEvent.getPhoneNumber();
    address = guestAddEvent.getAddress();
    isActive = Boolean.TRUE;
  }

  @EventSourcingHandler
  public void on(GuestRemoveEvent removeEvent) {
    log.info("Event sourcing on remove guest event: {}", removeEvent);
    isActive = Boolean.FALSE;
  }

}
