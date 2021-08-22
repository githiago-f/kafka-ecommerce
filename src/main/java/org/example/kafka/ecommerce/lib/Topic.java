package org.example.kafka.ecommerce.lib;

public enum Topic {
    NEW_ORDER("ECOMMERCE_NEW_ORDER");

    private final String label;
    Topic(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
