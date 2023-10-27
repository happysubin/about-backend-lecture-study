package io.springbatch.springbatchlecture.part6_flow.scope;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


@Configuration
@RequiredArgsConstructor
public class JobScope_StepScope_Configuration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                .start(step1(null))
                .next(step2())
                .listener(new JobListener())
                .build();
    }

    @Bean
    @JobScope
    public Step step1(@Value("#{jobParameters['message']}") String message) {
        System.out.println("message = " + message);
        return stepBuilderFactory.get("step1")
                .tasklet(tasklet1(null, null))
                .listener(new StepListener())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{stepExecutionContext['name']}") String name, @Value("#{jobExecutionContext['message']}") String message){
        return (stepContribution, chunkContext) -> {
            System.out.println("message = " + message);
            System.out.println("jobExecutionContext['name'] : " + name);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return (args) -> {
            CompletableFuture
                    .runAsync(() -> {
                        System.out.println(Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-3 출력됨. 직접 ExecutorService를 할당할 수 있다.
                    })
                    .thenRun(
                            () -> {
                                System.out.println(Thread.currentThread().getName());
                            }
                    )
                    .thenRun(() -> {
                        System.out.println(Thread.currentThread().getName());
                    });
        };
    }
}
