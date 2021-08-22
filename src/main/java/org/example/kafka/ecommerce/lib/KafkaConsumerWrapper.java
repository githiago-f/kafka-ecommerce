package org.example.kafka.ecommerce.lib;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collection;
import java.util.regex.Pattern;

public class KafkaConsumerWrapper {
    private final KafkaConsumer<String, String> consumer;

    public KafkaConsumerWrapper(Group group) {
        this.consumer = new KafkaConsumer<>(
                KafkaProps.consumerProperties(group)
        );
    }

    public KafkaConsumerWrapper subscribe(Pattern pattern) {
        this.consumer.subscribe(pattern);
        return this;
    }

    public KafkaConsumerWrapper subscribe(Collection<String> pattern) {
        this.consumer.subscribe(pattern);
        return this;
    }

    public void execute(RecordCallback<String> recordCallback) {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                for (var record : records) {
                    recordCallback.exec(record);
                }
            }
            Thread.yield();
        }
    }
}
