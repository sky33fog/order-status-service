package com.example.order_service.listener;

import com.example.order_service.model.OrderEvent;
import com.example.order_service.model.OrderProcessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderListener {

    private final KafkaTemplate<String, OrderProcessEvent> kafkaTemplate;

    @Value("${app.kafka.kafkaOrderStatusTopic}")
    private String orderStatusTopic;

    @KafkaListener(topics = "${app.kafka.kafkaOrderTopic}",
            groupId = "${app.kafka.kafkaOrderGroupId}",
            containerFactory = "kafkaOrderEventConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload OrderEvent event,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        kafkaTemplate.send(orderStatusTopic, new OrderProcessEvent("PROCESS", Instant.now()));
    }
}