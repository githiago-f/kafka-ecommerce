package org.example.kafka.ecommerce.services;

import org.example.kafka.ecommerce.lib.KafkaConsumerWrapper;
import org.example.kafka.ecommerce.lib.RecordCallback;

import java.util.regex.Pattern;

import static org.example.kafka.ecommerce.lib.Group.LOG;

public class LogService implements Runnable {
    private final KafkaConsumerWrapper consumer = new KafkaConsumerWrapper(LOG)
            .subscribe(Pattern.compile("ECOMMERCE.*"));

    @Override
    public void run() {
        RecordCallback<String> callback = record -> {
            System.out.println("--------------------------------");
            System.out.println("Log:::" + record.topic());
            System.out.println("Key:::" + record.key());
            System.out.println("Val:::" + record.value());
        };
        consumer.execute(callback);
    }
}
