package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {


                        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
                        String name = jobParameters.getString("name");
                        Long seq = jobParameters.getLong("seq");
                        Date date = jobParameters.getDate("date");
                        Double age = jobParameters.getDouble("age");

                        System.out.println("name = " + name);
                        System.out.println("seq = " + seq);
                        System.out.println("date = " + date);
                        System.out.println("age = " + age);

                        Map<String, Object> jobParametersMap = chunkContext.getStepContext().getJobParameters();

                        System.out.println("step1 was executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println("step2 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}

/**
 * java -jar springbatchlecture-0.0.1-SNAPSHOT.jar name=user123
 * 빌드시에 인자로 전달 가능
 */