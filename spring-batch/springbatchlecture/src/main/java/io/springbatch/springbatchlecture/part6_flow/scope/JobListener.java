package io.springbatch.springbatchlecture.part6_flow.scope;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {


    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().putString("message", "myMessage");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
