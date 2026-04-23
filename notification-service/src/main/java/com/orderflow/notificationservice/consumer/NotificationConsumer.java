package com.orderflow.notificationservice.consumer;

import com.orderflow.notificationservice.event.OrderCreatedEvent;
import com.orderflow.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${kafka.topics.order-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleOrderCreatedEvents(OrderCreatedEvent event){
        log.info("Received OrderCreatedEvent | orderId={}",
                event.getOrderId());
        notificationService.processOrderCreatedEvent(event);
    }
}
