package pl.radek.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.radek.events.OrderPlacedEvent;

@Service
public class OrderEventProducer {

    private final String topicName;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate,
                              @Value("${app.kafka.topic}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public OrderPlacedEvent sendOrderPlacedEvent(OrderPlacedEvent event) {
        kafkaTemplate.send(topicName, event.getOrderId(), event);
        return event;
    }
}
