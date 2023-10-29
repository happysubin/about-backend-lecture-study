package io.springbatch.springbatchlecture.part7_chunk.simple;

import lombok.Getter;

@Getter
public class Customer {

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                '}';
    }
}
