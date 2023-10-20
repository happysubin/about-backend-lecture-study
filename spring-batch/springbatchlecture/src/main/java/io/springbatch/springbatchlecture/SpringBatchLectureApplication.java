package io.springbatch.springbatchlecture;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan("io.springbatch.springbatchlecture.part3_domain_basic.jobrepository")
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchLectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchLectureApplication.class, args);
    }

}
