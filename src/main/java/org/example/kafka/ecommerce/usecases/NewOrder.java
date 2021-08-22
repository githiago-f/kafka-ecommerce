package org.example.kafka.ecommerce.usecases;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.kafka.ecommerce.lib.KafkaService;

import java.util.UUID;

import static org.example.kafka.ecommerce.lib.Topic.NEW_ORDER;

public class NewOrder {
    private final KafkaService<String> kafkaService;

    public NewOrder() {
        kafkaService = new KafkaService<>((data, e) -> {
            if(e!=null) {
                e.printStackTrace();
                return;
            }
            System.out.println(
                "Message sent to " + data.topic() + ":::" + data.partition() + " at " + data.offset()
            );
        });
    }

    public void execute(UUID userId) {
        String key = userId.toString();
        String value = "{Product=1, Price=123, UserId="+key+"}";
        var record = new ProducerRecord<>(NEW_ORDER.getLabel(), key, value);
        kafkaService.sendRecord(record);
        Thread.yield();
    }
}
