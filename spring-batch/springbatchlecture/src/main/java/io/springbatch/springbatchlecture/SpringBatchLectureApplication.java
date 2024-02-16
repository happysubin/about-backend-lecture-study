package io.springbatch.springbatchlecture;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

//@ComponentScan("io.springbatch.springbatchlecture.playground")
//@ComponentScan("io.springbatch.springbatchlecture.part11_repeat_errorcontrol.retry.template")
@ComponentScan("io.springbatch.springbatchlecture.part7_chunk.simple")
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchLectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchLectureApplication.class, args);
    }

}
