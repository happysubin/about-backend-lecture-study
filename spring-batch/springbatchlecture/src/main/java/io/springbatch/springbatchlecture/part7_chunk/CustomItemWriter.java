package io.springbatch.springbatchlecture.part7_chunk;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomItemWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.forEach(System.out::println);
    }
}
