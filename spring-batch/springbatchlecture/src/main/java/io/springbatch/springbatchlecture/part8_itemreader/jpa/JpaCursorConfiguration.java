package io.springbatch.springbatchlecture.part8_itemreader.jdbc;

import com.zaxxer.hikari.HikariConfig;
import io.springbatch.springbatchlecture.part8_itemreader.json.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcCursorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private int chunkSize = 10;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob123")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Member, Member>chunk(chunkSize)
                .reader(customerItemReader())
                .writer(customItemWriter())
                .build();
    }


    @Bean
    public ItemReader<? extends Member> customerItemReader() {
        return new JdbcCursorItemReaderBuilder<Member>()
                .name("jdbcCursorItemReader")
                .fetchSize(chunkSize) //일반적으로 청크 사이즈와 동일하게 한다.
                .sql("select * from member order by id")
                .beanRowMapper(Member.class)
                //.queryArguments("A%")
                .dataSource(dataSource)
                .build();

    }

    @Bean
    public ItemWriter<Member> customItemWriter() {
        return members -> {
            for (Member member : members) {
                System.out.println("member = " + member);
            }
        };
    }
}
