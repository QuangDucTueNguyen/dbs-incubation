package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.template.CreateGuest;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public final class ConsumerService {

  private final UserService userService;

  public ConsumerService(UserService userService) {
    this.userService = userService;
  }

  @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
  public void consume(CreateGuest createGuest) {
    log.info("Consumed message: {}", createGuest);
    Optional.ofNullable(createGuest)
        .filter(cg -> "FAILED".equals(cg.getStatus()))
        .ifPresent(cg -> userService.delete(cg.getId()));
  }

}
