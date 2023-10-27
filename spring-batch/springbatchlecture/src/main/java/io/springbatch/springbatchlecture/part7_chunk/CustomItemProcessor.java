package io.springbatch.springbatchlecture.part7_chunk;

import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;

public class CustomItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        return new Customer(item.getName().toUpperCase(Locale.ROOT));
    }
}
