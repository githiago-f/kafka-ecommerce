package org.example.kafka.ecommerce.usecases;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.kafka.ecommerce.entities.Email;
import org.example.kafka.ecommerce.entities.Order;
import org.example.kafka.ecommerce.lib.KafkaProducerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.kafka.ecommerce.lib.Topic.SEND_EMAIL;

public class SendEmail implements AutoCloseable, UseCase<Order> {
    private final Logger logger = LoggerFactory.getLogger(NewOrder.class.getSimpleName());
    private final KafkaProducerWrapper<String, Email> emailService;

    public SendEmail() {
        emailService = new KafkaProducerWrapper<>((data, e) -> {
            if(e!=null) {
                logger.error(e.getMessage());
            }
        });
    }

    public void execute(Order order) {
        Email email = new Email(
                "We got your order!",
                "The order " + order.getId() + " is being prepared to be sent!"
        );
        var record = new ProducerRecord<>(
                SEND_EMAIL.getLabel(),
                order.getProductId().toString(),
                email
        );
        emailService.sendRecord(record);
    }

    @Override
    public void close() {
        emailService.close();
    }
}
