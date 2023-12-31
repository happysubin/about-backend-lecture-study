package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.api;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class RetryItemWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> items) throws Exception {
        items.forEach(item -> System.out.println(item));
    }
}
