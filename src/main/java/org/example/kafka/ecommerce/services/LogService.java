package org.example.kafka.ecommerce.services;

import org.example.kafka.ecommerce.entities.Order;
import org.example.kafka.ecommerce.lib.KafkaConsumerWrapper;
import org.example.kafka.ecommerce.lib.RecordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

import static org.example.kafka.ecommerce.lib.Group.LOG;

public class LogService implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(LogService.class.getSimpleName());
    private final Pattern topics = Pattern.compile("ECOMMERCE.*");

    @Override
    public void run() {
        try(var consumer = new KafkaConsumerWrapper<>(String.class, LOG).subscribe(topics)) {
            RecordCallback<String> callback = record -> {
                logger.info("--------------------------------");
                logger.info("Topic:::" + record.topic());
                logger.info(record.key() + " ::: " + record.value());
            };
            consumer.execute(callback);
        }
    }
}
