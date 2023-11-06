package io.springbatch.springbatchlecture.part9_itemwriter.json;

import io.springbatch.springbatchlecture.part9_itemwriter.flatfile.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JsonFileItemWriterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob@123")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(10)
                .reader(itemReader())
                .writer(customWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemWriter<? super Customer> customWriter() {
        return new JsonFileItemWriterBuilder<>()
                .name("jsonFileWriter")
                .resource(new FileSystemResource("/Users/ansubin/Desktop/lecture-study/spring-batch/springbatchlecture/src/main/resources/result.json"))
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .shouldDeleteIfExists(true)
                .build();
    }


    @Bean
    public ItemReader<Customer> itemReader() {
        List<Customer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Customer(i, "name " + i, 20 + i ));
        }
        return new ListItemReader<>(list);
    }

}
