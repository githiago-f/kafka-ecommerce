package org.example.kafka.ecommerce.services;

import org.example.kafka.ecommerce.entities.Order;
import org.example.kafka.ecommerce.lib.KafkaConsumerWrapper;
import org.example.kafka.ecommerce.lib.RecordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.example.kafka.ecommerce.lib.Group.EMAIL;
import static org.example.kafka.ecommerce.lib.Topic.NEW_ORDER;

public class EmailService implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class.getSimpleName());
    private final List<String> topics = List.of(NEW_ORDER.getLabel());

    @Override
    public void run() {
        try(var consumer = new KafkaConsumerWrapper<Order>(EMAIL).subscribe(topics)) {
            RecordCallback<Order> callback = record -> {
                logger.info("--------------------------------");
                logger.info("Sending e-mail -> " + record.offset());
                logger.info("Key:::" + record.key());
                logger.info("Val:::" + record.value());
            };
            consumer.execute(callback);
        }
    }
}
