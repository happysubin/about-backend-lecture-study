package io.springbatch.springbatchlecture;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Job을 정의
 */

@Configuration
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory; //job 생성 빌더 팩토리
    private final StepBuilderFactory stepBuilderFactory; //step 생성 빌더 팩토리

    public HelloJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob") //Job 생성
                .start(helloStep())
                .next(helloStep2())
                .build();
    }

    /**
     * tasklet -> step 안에서 단일 태스크로 수행되는 로직 구현
     *
     * Job 구동 (Step은 여러 개) -> Step을 실행 -> Tasklet을 실행
     */

    @Bean
    public Step helloStep() {
        return stepBuilderFactory.get("helloStep") //Step 생성
                .tasklet(((contribution, chunkContext) -> { //기본은 무한 반복
                    System.out.println("===================");
                    System.out.println("Hello Spring Batch");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2") //Step 생성
                .tasklet(((contribution, chunkContext) -> { //기본은 무한 반복
                    System.out.println("===================");
                    System.out.println("Hello Spring Batch 2");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
