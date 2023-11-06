package io.springbatch.springbatchlecture.part8_itemreader.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ItemReaderAdapterConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob@")
                .start(step1())
                .build();

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(10)
                .reader(itemReader())
                .writer(customWriter())
                .build();

    }

    @Bean
    public ItemReader<String> itemReader() {
        ItemReaderAdapter<String> adapter = new ItemReaderAdapter<>();
        adapter.setTargetObject(customService());
        adapter.setTargetMethod("read");
        return adapter;
    }

    @Bean
    public CustomService customService() {
        return customService();
    }

    @Bean
    public ItemWriter<? super String> customWriter() {
        return items -> {
            System.out.println("items = " + items);
        };
    }
}
