package io.springbatch.springbatchlecture.part3_domain_basic.jobrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRepositoryListener implements JobExecutionListener {

    private final JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        JobParameters jobParameters1 = new JobParametersBuilder()
                .toJobParameters();

        JobExecution job = jobRepository.getLastJobExecution("rJob", jobParameters1);
        JobInstance jobInstance = jobExecution.getJobInstance();
        StepExecution rStep1 = jobRepository.getLastStepExecution(jobInstance, "rStep1");
        System.out.println("rStep1 = " + rStep1);

        System.out.println("job = " + job);

    }
}
