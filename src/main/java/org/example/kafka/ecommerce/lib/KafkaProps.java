package org.example.kafka.ecommerce.lib;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProps {
    private final Properties props;
    KafkaProps() {
        props = new Properties();
    }

    public KafkaProps prop(String key, String value) {
        props.setProperty(key, value);
        return this;
    }

    public Properties getProps() {
        return props;
    }

    public static Properties producerProperties(String server) {
        var props = new KafkaProps();
        var serializerName = StringSerializer.class.getName();
        return props
                .prop(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server)
                .prop(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializerName)
                .prop(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializerName)
                .getProps();
    }

    public static Properties producerProperties() {
        return producerProperties("localhost:9092");
    }

    public static Properties consumerProperties(Group group) {
        var props = new KafkaProps();
        var deserializer = StringDeserializer.class.getName();

        return props
            .prop(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            .prop(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deserializer)
            .prop(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
            .prop(ConsumerConfig.GROUP_ID_CONFIG, group.getName())
            .prop(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
            .getProps();
    }
}
