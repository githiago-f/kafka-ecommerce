package org.example.kafka.ecommerce.lib;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ExecutionException;

public class KafkaProducerWrapper<K, T> implements AutoCloseable {
    private final KafkaProducer<K, T> producer;
    private final Callback callback;

    public KafkaProducerWrapper(Callback callback) {
        this.callback = callback;
        producer = new KafkaProducer<>(
            KafkaProps.producerProperties()
        );
    }

    public KafkaProducerWrapper() {
        producer = new KafkaProducer<>(
                KafkaProps.producerProperties()
        );
        callback = (data, e) -> {};
    }

    public void sendRecord(ProducerRecord<K, T> record) {
        try {
            producer.send(record, this.callback).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void sendRecord(ProducerRecord<K, T> record, Callback callback) {
        try {
            producer.send(record, callback).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        producer.close();
    }
}
