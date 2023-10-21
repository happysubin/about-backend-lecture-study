package io.springbatch.springbatchlecture.part3_domain_basic.jobrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobRepositoryConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobRepositoryListener jobRepositoryListener;


    @Bean
    public Job job() {
        return jobBuilderFactory.get("rJob1")
                .start(jRBean1())
                .next(jRBean2())
                .listener(jobRepositoryListener)
                .build();
    }

    @Bean
    public Step jRBean1() {
        return stepBuilderFactory.get("rStep1")
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    Thread.sleep(3000);
                    System.out.println("rStep1 executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step jRBean2() {
        return stepBuilderFactory.get("rStep2")
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println("rStep2 executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
