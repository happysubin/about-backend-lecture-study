package io.springbatch.springbatchlecture.part9_itemwriter.adapter;

import io.springbatch.springbatchlecture.part9_itemwriter.jdbc.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class ItemWriterAdapterConfiguration {

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
                .<Member, Member>chunk(10)
                .reader(customReader())
                .writer(customWriter())
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public ItemWriter<? super Member> customWriter() {
        ItemWriterAdapter<Member> writer = new ItemWriterAdapter<>();
        writer.setTargetObject(new CustomService<>());
        writer.setTargetMethod("customWrite");
        return writer;
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
