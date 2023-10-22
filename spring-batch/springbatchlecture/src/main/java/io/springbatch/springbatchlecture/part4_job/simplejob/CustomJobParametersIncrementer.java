package io.springbatch.springbatchlecture.part4_job.simplejob;

import org.springframework.batch.core.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomJobParametersIncrementer implements JobParametersIncrementer {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD-hhmmss");

    @Override
    public JobParameters getNext(JobParameters parameters) {
        String id = CustomJobParametersIncrementer.format.format(new Date());
        return new JobParametersBuilder()
                .addString("run.id", id)
                .toJobParameters();
    }
}
