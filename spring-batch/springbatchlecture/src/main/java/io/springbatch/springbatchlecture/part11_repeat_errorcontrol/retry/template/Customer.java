package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.template;

import lombok.Data;
import lombok.Getter;

@Getter
public class Customer {

    private String name;

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
