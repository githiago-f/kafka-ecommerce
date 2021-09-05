package org.example.kafka.ecommerce;

import org.example.kafka.ecommerce.entities.Order;
import org.example.kafka.ecommerce.services.EmailService;
import org.example.kafka.ecommerce.services.FraudDetectorService;
import org.example.kafka.ecommerce.services.LogService;
import org.example.kafka.ecommerce.usecases.NewOrder;
import org.example.kafka.ecommerce.usecases.SendEmail;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

public class KafkaTestMain {
    public static void main(String[] args) throws InterruptedException {
        var executor = Executors.newFixedThreadPool(4);

        List<Runnable> threads = List.of(
                new FraudDetectorService(),
                new EmailService(),
                new LogService()
        );

        for (var thread : threads) { executor.submit(thread); }

        try (var emailService = new SendEmail()) {
            try (var command = new NewOrder(emailService)) {
                for (int i = 0; i < 10; i++) {
                    var order = new Order(
                            UUID.randomUUID(),
                            UUID.randomUUID(),
                            BigDecimal.valueOf(Math.random() * 5000 + 1)
                    );
                    command.execute(order);
                }

                Thread.sleep(5000);
            }
        }

        executor.shutdown();
    }
}
