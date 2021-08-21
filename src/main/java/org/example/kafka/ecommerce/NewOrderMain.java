package org.example.kafka.ecommerce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.example.kafka.ecommerce.Topics.NEW_ORDER;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        String value = "{Product=1, Price=123, UserId=1}";
        var record = new ProducerRecord<>(NEW_ORDER.getLabel(), value, value);
        producer.send(record, (data, e) -> {
            if(e!=null) {
                e.printStackTrace();
                return;
            }
            System.out.println(
                    "Message sent to " + data.topic() + ":::" + data.partition() + " at " + data.offset());
        }).get();
    }

    protected static Properties properties() {
        var props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        var serializerName = StringSerializer.class.getName();
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializerName);
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializerName);
        return props;
    }
}
