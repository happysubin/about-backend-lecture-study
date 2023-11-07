package io.springbatch.springbatchlecture.part9_itemwriter.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcBatchItemWriterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;


    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("ba123tchJob")
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Member, Member>chunk(10)
                .reader(customReader())
                .writer(customWriter())
                .build();
    }

    @Bean
    public ItemReader<? extends Member> customReader() {
        return new JdbcCursorItemReaderBuilder<Member>()
                .name("jdbcCursorItemReader")
                .fetchSize(10)
                .sql("select * from member order by id")
                .beanRowMapper(Member.class)
                .dataSource(dataSource)
                .build();

    }

    @Bean
    public ItemWriter<? super Member> customWriter() {
        String sql = "insert into member2 (id, created_at, status, updated_at, email, password, auth_provider, name, profile_img, role, thumbnail_img) \n" +
                " values (:id, :createdAt, :status, :updatedAt, :email, :password, :authProvider, :name, :profileImg, :role, :thumbnailImg)";
        return new JdbcBatchItemWriterBuilder<Member>()
                .dataSource(dataSource)
                .sql(sql)
                .beanMapped()
                .build();
    }
}
