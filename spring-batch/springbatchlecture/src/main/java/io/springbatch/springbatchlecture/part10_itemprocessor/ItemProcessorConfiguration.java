package io.springbatch.springbatchlecture.part10_itemprocessor;

import io.springbatch.springbatchlecture.part9_itemwriter.flatfile.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ItemProcessorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob@123")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(10)
                .reader(itemReader())
                .processor(customProcessor())
                .writer(customWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemProcessor<? super String, String> customProcessor() {
        List list = new ArrayList<>();
        list.add(new CustomItemProcessor1());
        list.add(new CustomItemProcessor2());
        return new CompositeItemProcessorBuilder<>()
                .delegates(list)
                .build();
    }

    @Bean
    public ItemWriter<String> customWriter() {
        return (items) -> {
            for (String item : items) {
                System.out.println("item = " + item);
            }
        };
    }


    @Bean
    public ItemReader<String> itemReader() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("name");
        }
        return new ListItemReader<>(list);
    }
}
