package io.springbatch.springbatchlecture.part4_job.simplejob;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class CustomJobParametersValidator implements JobParametersValidator {

    /**
     * 총 2번 검증한다.
     * 레포지토리에서 어떤 작업을 하기 전에
     * SimpleJob에서 실제 작업을 하기 전에
     */
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if(parameters.getString("name") == null) {
            throw new JobParametersInvalidException("name parameter is not found");
        }
    }
}
