package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.template;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class RetryItemWriter2 implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.forEach(item -> System.out.println(item));
    }
}
