package io.springbatch.springbatchlecture.part10_itemprocessor;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<String, String> {

    int cnt = 0;


    @Override
    public String process(String item) throws Exception {
        return item + cnt++;
    }
}
