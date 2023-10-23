package io.springbatch.springbatchlecture.part5_step.taskletstep;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class TaskletConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                //.incrementer(new RunIdIncrementer())
                .start(taskStep())
                .next(step2())
                .build();
    }

    @Bean
    public Step taskStep() {
        return stepBuilderFactory.get("taskStep")
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                }))
                .allowStartIfComplete(true) //항상 재시작
                .startLimit(10) //실행 횟수 조정.
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("chunkStep")
                .<String, String> chunk(3)
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return item.toUpperCase(Locale.ROOT);
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {
                        for (String item : items) {
                            System.out.println("item = " + item);
                        }
                    }
                })
                .build();
    }
}
