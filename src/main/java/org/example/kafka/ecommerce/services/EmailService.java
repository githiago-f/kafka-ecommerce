package org.example.kafka.ecommerce.services;

import org.example.kafka.ecommerce.lib.KafkaConsumerWrapper;
import org.example.kafka.ecommerce.lib.RecordCallback;

import java.util.List;

import static org.example.kafka.ecommerce.lib.Group.EMAIL;
import static org.example.kafka.ecommerce.lib.Topic.NEW_ORDER;

public class EmailService implements Runnable {
    private final KafkaConsumerWrapper consumer = new KafkaConsumerWrapper(EMAIL)
            .subscribe(List.of(NEW_ORDER.getLabel()));

    @Override
    public void run() {
        RecordCallback<String> callback = record -> {
            System.out.println("--------------------------------");
            System.out.println("Sending e-mail -> " + record.offset());
            System.out.println("Key:::" + record.key());
            System.out.println("Val:::" + record.value());
        };
        consumer.execute(callback);
    }
}
