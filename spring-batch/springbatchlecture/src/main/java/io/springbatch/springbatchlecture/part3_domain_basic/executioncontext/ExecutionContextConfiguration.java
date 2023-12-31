package io.springbatch.springbatchlecture.part3_domain_basic.executioncontext;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Configuration
public class ExecutionContextConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExecutionContextTasklet1 executionContextTasklet1;
    private final ExecutionContextTasklet2 executionContextTasklet2;
    private final ExecutionContextTasklet3 executionContextTasklet3;

    @Bean
    public Job BatchJob() {
        return this.jobBuilderFactory.get("eeJeob")
                .start(eStep1())
                .next(eStep2())
                .next(eStep3())
                .build();
    }

//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("step1 has executed");
//                        return RepeatStatus.FINISHED;
//                    }
//                })
//                .build();
//    }
//    @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("step2")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("step2 has executed");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }

    @Bean
    public Step eStep1() {
        return stepBuilderFactory.get("eStep1")
                .tasklet(executionContextTasklet1)
                .build();
    }
    @Bean
    public Step eStep2() {
        return stepBuilderFactory.get("eStep2")
                .tasklet(executionContextTasklet2)
                .build();
    }
    @Bean
    public Step eStep3() {
        return stepBuilderFactory.get("eStep3")
                .tasklet(executionContextTasklet3)
                .build();
    }
}
