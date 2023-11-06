package io.springbatch.springbatchlecture.part9_itemwriter.flatfile;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FlatFileItemWriterConfiguration {

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
                .writer(customWriter2())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemWriter<? super Customer> customWriter() {
        return new FlatFileItemWriterBuilder<>()
                .name("flatFileWriter")
                .resource(new FileSystemResource("/Users/ansubin/Desktop/lecture-study/spring-batch/springbatchlecture/src/main/resources/result.txt"))
                .delimited()
                .delimiter("|")
                .names(new String[]{"id", "name", "age"})
                .append(true)
                .build();
    }

    @Bean
    public ItemWriter<? super Customer> customWriter2() {
        return new FlatFileItemWriterBuilder<>()
                .name("flatFileWriter")
                .resource(new FileSystemResource("/Users/ansubin/Desktop/lecture-study/spring-batch/springbatchlecture/src/main/resources/result2.txt"))
                .formatted()
                .format("%-2d%-6s%-2d")
                .names(new String[]{"id", "name", "age"})
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
