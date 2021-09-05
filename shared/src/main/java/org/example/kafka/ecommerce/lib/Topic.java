package org.example.kafka.ecommerce.lib;

public enum Topic {
    NEW_ORDER("ECOMMERCE_NEW_ORDER"),
    SEND_EMAIL("ECOMMERCE_SEND_EMAIL");

    private final String label;
    Topic(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
