package io.springbatch.springbatchlecture.part8_itemreader.jpa.cursor;


import io.springbatch.springbatchlecture.part8_itemreader.jpa.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class JpaCursorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<MemberEntity, MemberEntity>chunk(chunkSize)
                .reader(customerItemReader())
                .writer(customItemWriter())
                .build();
    }


    @Bean
    public ItemReader<? extends MemberEntity> customerItemReader() {
        return new JpaCursorItemReaderBuilder<MemberEntity>()
                .name("jpaCursorItemReaer")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select m from MemberEntity m order by id")
                //.parameterValues()
                //.maxItemCount(chunkSize)
                //.currentItemCount(2)
                .build();
    }

    @Bean
    public ItemWriter<MemberEntity> customItemWriter() {
        return members -> {
            for (MemberEntity member : members) {
                System.out.println("member = " + member);
            }
        };
    }
}
