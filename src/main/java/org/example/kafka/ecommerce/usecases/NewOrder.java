package org.example.kafka.ecommerce.usecases;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.kafka.ecommerce.entities.Order;
import org.example.kafka.ecommerce.lib.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.kafka.ecommerce.lib.Topic.NEW_ORDER;

public class NewOrder {
    private final Logger logger = LoggerFactory.getLogger(NewOrder.class.getSimpleName());
    private final KafkaService<String, Order> kafkaService;

    public NewOrder() {
        kafkaService = new KafkaService<>((data, e) -> {
            if(e!=null) {
                logger.error(e.getMessage());
            }
        });
    }

    public void execute(Order order) {
        var record = new ProducerRecord<>(
                NEW_ORDER.getLabel(),
                order.getProductId().toString(),
                order
        );
        kafkaService.sendRecord(record);
    }
}
