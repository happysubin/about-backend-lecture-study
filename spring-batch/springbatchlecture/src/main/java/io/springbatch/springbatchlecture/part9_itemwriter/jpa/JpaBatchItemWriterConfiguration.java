package io.springbatch.springbatchlecture.part9_itemwriter.jpa;

import io.springbatch.springbatchlecture.part8_itemreader.jpa.MemberEntity;
import io.springbatch.springbatchlecture.part9_itemwriter.jdbc.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JpaBatchItemWriterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;


    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Member, MemberEntity>chunk(10)
                .reader(customReader())
                .processor(itemProcessor())
                .writer(customWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemProcessor<Member, MemberEntity> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<? super MemberEntity> customWriter() {
        return new JpaItemWriterBuilder<MemberEntity>()
                .usePersist(true)// true면 persist처리, false면 merge 처리
                .entityManagerFactory(entityManagerFactory)
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

}
