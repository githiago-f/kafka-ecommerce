package org.example.kafka.ecommerce;

public enum Group {
    FRAUD_DETECTION("FraudDetectionServices"),
    LOG("LogService"),
    EMAIL("EmailService");

    private final String name;
    Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
