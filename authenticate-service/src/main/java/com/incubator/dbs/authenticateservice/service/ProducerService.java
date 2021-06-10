package com.incubator.dbs.authenticateservice.service;

import com.incubator.dbs.authenticateservice.model.template.CreateGuest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public final class ProducerService {

  private final KafkaTemplate<String, CreateGuest> kafkaTemplate;
  @Value("${spring.kafka.producer.topic}")
  private String kafkaTopic;

  public ProducerService(KafkaTemplate<String, CreateGuest> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(CreateGuest message) {
    log.info("Producing message: {}", message);

    ListenableFuture<SendResult<String, CreateGuest>> future = this.kafkaTemplate.send(kafkaTopic, message);
    future.addCallback(new ListenableFutureCallback<>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error("Unable to send message=[ {} ] due to : {}", message, ex.getMessage());
      }

      @Override
      public void onSuccess(SendResult<String, CreateGuest> result) {
        log.info("Sent message=[ {} ] with offset=[ {} ]", message, result.getRecordMetadata().offset());
      }
    });
  }
}
