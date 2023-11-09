package io.springbatch.springbatchlecture.part10_itemprocessor;

import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;

public class CustomItemProcessor1 implements ItemProcessor<String, String> {


    @Override
    public String process(String item) throws Exception {
        return item.toUpperCase(Locale.ROOT);
    }
}
