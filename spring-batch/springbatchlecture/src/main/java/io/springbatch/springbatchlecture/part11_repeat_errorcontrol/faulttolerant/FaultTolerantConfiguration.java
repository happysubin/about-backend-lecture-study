package io.springbatch.springbatchlecture.part11_repeat_errorcontrol.faulttolerant;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FaultTolerantConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(new ItemReader<String>() {
                    int i = 0;
                    @Override
                    public String read() {
                        i++;
                        if(i == 1) {
                            throw new IllegalArgumentException("skip");
                        }
                        return i > 3 ? null : "item" + i;
                    }
                })
                .processor((ItemProcessor<String, String>) item -> {
                    throw new IllegalStateException("retry");
//                    return item;
                })
                .writer(items -> System.out.println(items))
                .faultTolerant()
                .skip(IllegalArgumentException.class)// writer, reader, processor 모두 적용 가능
                .skipLimit(1)
                .retry(IllegalStateException.class)// writer, processor 적용 가능
                .retryLimit(2)
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public SimpleLimitExceptionHandler simpleLimitExceptionHandler(){
        return new SimpleLimitExceptionHandler(3);
    }
}
