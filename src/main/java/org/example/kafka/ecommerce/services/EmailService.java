package org.example.kafka.ecommerce.services;

import org.example.kafka.ecommerce.entities.Email;
import org.example.kafka.ecommerce.lib.KafkaConsumerWrapper;
import org.example.kafka.ecommerce.lib.RecordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.example.kafka.ecommerce.lib.Group.EMAIL;
import static org.example.kafka.ecommerce.lib.Topic.SEND_EMAIL;

public class EmailService implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class.getSimpleName());
    private final List<String> topics = List.of(SEND_EMAIL.getLabel());

    @Override
    public void run() {
        try(var consumer = new KafkaConsumerWrapper<>(Email.class, EMAIL).subscribe(topics)) {
            RecordCallback<Email> callback = record -> {
                logger.info("--------------------------------");
                logger.info("Sending e-mail -> " + record.offset());
                logger.info("Key:::" + record.key());
                logger.info("Email to send:::" + record.value().getSubject());
            };
            consumer.execute(callback);
        }
    }
}
