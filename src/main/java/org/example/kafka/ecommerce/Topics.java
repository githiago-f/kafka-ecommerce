package org.example.kafka.ecommerce;

public enum Topics {
    NEW_ORDER("ECOMMERCE_NEW_ORDER");

    private final String label;
    Topics(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
