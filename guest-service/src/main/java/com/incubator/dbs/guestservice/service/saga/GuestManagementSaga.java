package com.incubator.dbs.guestservice.service.saga;

import com.incubator.dbs.guestservice.model.event.GuestAddEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

@Saga
@Slf4j
public class GuestManagementSaga {

  @StartSaga
  @SagaEventHandler(associationProperty = "id")
  public void handle(GuestAddEvent guestAddEvent){
    log.info("Saga handle add guest event. {}", guestAddEvent);
    //To do something
  }
}
