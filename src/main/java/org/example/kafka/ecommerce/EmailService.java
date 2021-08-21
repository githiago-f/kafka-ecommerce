package org.example.kafka.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.example.kafka.ecommerce.Group.EMAIL;
import static org.example.kafka.ecommerce.Topics.NEW_ORDER;

public class EmailService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<>(properties());
        consumer.subscribe(List.of(NEW_ORDER.getLabel()));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(1000));
            if (!records.isEmpty()) {
                for (var record : records) {
                    System.out.println("--------------------------------");
                    System.out.println("Log:::" + record.topic());
                    System.out.println("Key:::" + record.key());
                    System.out.println("Val:::" + record.value());
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
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EMAIL.getName());

        return props;
    }
}
