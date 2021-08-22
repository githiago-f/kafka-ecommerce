package org.example.kafka.ecommerce;

import org.example.kafka.ecommerce.services.EmailService;
import org.example.kafka.ecommerce.services.FraudDetectorService;
import org.example.kafka.ecommerce.services.LogService;
import org.example.kafka.ecommerce.usecases.NewOrder;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

public class KafkaTestMain {
    public static void main(String[] args) {
        var executor = Executors.newFixedThreadPool(4);

        List<Runnable> threads = List.of(
                new FraudDetectorService(),
                new FraudDetectorService(),
                new EmailService(),
                new LogService()
        );

        for (var thread : threads) {
            executor.submit(thread);
        }

        var command = new NewOrder();

        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());
        command.execute(UUID.randomUUID());

        executor.shutdown();
    }
}