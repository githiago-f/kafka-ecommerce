package org.example.kafka.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.example.kafka.ecommerce.Group.FRAUD_DETECTION;
import static org.example.kafka.ecommerce.Topics.NEW_ORDER;

public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<>(properties());
        consumer.subscribe(List.of(NEW_ORDER.getLabel()));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(1000));
            if (!records.isEmpty()) {
                System.out.println("Found " + records.count() + " orders");
                for (var record : records) {
                    System.out.println("Checking record for fraud = " + record);
                }
            }
        }
    }

    private static Properties properties() {
        var props = new Properties();
        var deserializer = StringDeserializer.class.getName();

        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deserializer);
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FRAUD_DETECTION.getName());

        return props;
    }
}
