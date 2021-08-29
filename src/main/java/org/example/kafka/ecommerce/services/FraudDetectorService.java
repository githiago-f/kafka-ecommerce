package org.example.kafka.ecommerce.services;

import org.example.kafka.ecommerce.entities.Order;
import org.example.kafka.ecommerce.lib.KafkaConsumerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.example.kafka.ecommerce.lib.Group.FRAUD_DETECTION;
import static org.example.kafka.ecommerce.lib.Topic.NEW_ORDER;

public class FraudDetectorService implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(FraudDetectorService.class.getName());
    List<String> topics = List.of(NEW_ORDER.getLabel());

    @Override
    public void run() {
        try(var consumer = new KafkaConsumerWrapper<Order>(FRAUD_DETECTION).subscribe(topics)) {
            consumer.execute(record -> logger.info("Checking order for fraud = " + record.value().getId()));
        }
    }
}
