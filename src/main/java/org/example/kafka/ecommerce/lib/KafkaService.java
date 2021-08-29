package org.example.kafka.ecommerce.lib;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ExecutionException;

public class KafkaService<K, T> {
    private final KafkaProducer<K, T> producer;
    private final Callback callback;

    public KafkaService(Callback callback) {
        this.callback = callback;
        producer = new KafkaProducer<>(
            KafkaProps.producerProperties()
        );
    }

    public void sendRecord(ProducerRecord<K, T> record) {
        try {
            producer.send(record, this.callback).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
