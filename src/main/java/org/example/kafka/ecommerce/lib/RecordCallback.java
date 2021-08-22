package org.example.kafka.ecommerce.lib;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface RecordCallback<T> {
    void exec(ConsumerRecord<T, T> record);
}
