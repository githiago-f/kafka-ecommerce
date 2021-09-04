package org.example.kafka.ecommerce.usecases;

public interface UseCase<T> {
    void execute(T entity);
}
