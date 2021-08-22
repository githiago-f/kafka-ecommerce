package org.example.kafka.ecommerce.services;

import org.example.kafka.ecommerce.lib.KafkaConsumerWrapper;
import org.example.kafka.ecommerce.lib.RecordCallback;

import java.util.List;

import static org.example.kafka.ecommerce.lib.Group.FRAUD_DETECTION;
import static org.example.kafka.ecommerce.lib.Topic.NEW_ORDER;

public class FraudDetectorService implements Runnable {
    private final KafkaConsumerWrapper consumer = new KafkaConsumerWrapper(FRAUD_DETECTION)
            .subscribe(List.of(NEW_ORDER.getLabel()));

    @Override
    public void run() {
        RecordCallback<String> callback = record -> {
            System.out.println("----------------------------------");
            System.out.println("Checking record for fraud = " + record.value());
        };
        consumer.execute(callback);
    }
}
