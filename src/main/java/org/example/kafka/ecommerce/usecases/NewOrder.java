package org.example.kafka.ecommerce.usecases;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.kafka.ecommerce.entities.Order;
import org.example.kafka.ecommerce.lib.KafkaProducerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.kafka.ecommerce.lib.Topic.NEW_ORDER;

public class NewOrder implements AutoCloseable, UseCase<Order> {
    private final Logger logger = LoggerFactory.getLogger(NewOrder.class.getSimpleName());
    private final KafkaProducerWrapper<String, Order> orderService;
    private final UseCase<Order> emailService;

    public NewOrder(UseCase<Order> emailService) {
        orderService = new KafkaProducerWrapper<>();
        this.emailService = emailService;
    }

    public void execute(Order order) {
        var record = new ProducerRecord<>(
                NEW_ORDER.getLabel(),
                order.getProductId().toString(),
                order
        );
        orderService.sendRecord(record, (data, e) -> {
            if(e!=null) {
                logger.error(e.getMessage());
            }
            emailService.execute(order);
        });
    }

    @Override
    public void close() {
        orderService.close();
    }
}
