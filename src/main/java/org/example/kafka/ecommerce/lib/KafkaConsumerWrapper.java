package org.example.kafka.ecommerce.lib;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

public class KafkaConsumerWrapper<T> implements AutoCloseable {
    private final KafkaConsumer<String, T> consumer;
    private Boolean kill = false;

    public KafkaConsumerWrapper(Group group) {
        this.consumer = new KafkaConsumer<>(
                KafkaProps.consumerProperties(group)
        );
    }

    public KafkaConsumerWrapper<T> subscribe(Pattern pattern) {
        this.consumer.subscribe(pattern);
        return this;
    }

    public KafkaConsumerWrapper<T> subscribe(Collection<String> pattern) {
        this.consumer.subscribe(pattern);
        return this;
    }

    public void execute(RecordCallback<T> recordCallback) {
        do {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                for (var record : records) {
                    if(Objects.equals(record.value(), "END")) {
                        kill = true;
                    }
                    recordCallback.exec(record);
                }
            }
            Thread.yield();
        } while (!kill);
    }

    @Override
    public void close() {
        this.consumer.close();
    }
}
