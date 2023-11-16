package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.api;

import io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.RetryableException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class RetryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob1")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
//                .skip(RetryableException.class)
//                .skipLimit(2)
                //.retry(RetryableException.class)
                //.noRetry(NoRetryException.class)
                //.retryLimit(2)
                .retryPolicy(limitCheckingItemSkipPolicy())
                .build();
    }

    @Bean
    public SimpleRetryPolicy limitCheckingItemSkipPolicy() {

        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(RetryableException.class, true);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, exceptionClass);

        return simpleRetryPolicy;
    }

    @Bean
    public ListItemReader<String> reader() {

        List<String> items = new ArrayList<>();

        for(int i = 0; i < 30; i++) {
            items.add(String.valueOf(i));
        }

        return new ListItemReader<>(items);
    }


    @Bean
    public ItemProcessor processor() {
        RetryItemProcessor processor = new RetryItemProcessor();
        return processor;
    }

    @Bean
    public ItemWriter writer() {
        RetryItemWriter writer = new RetryItemWriter();
        return writer;
    }
}
