package io.springbatch.springbatchlecture.part8_itemreader.jdbc.paging;

import io.springbatch.springbatchlecture.part8_itemreader.jdbc.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JdbcPagingConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private int chunkSize = 10;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJo12123b")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<Member, Member>chunk(chunkSize)
                .reader(customerItemReader())
                .writer(customItemWriter())
                .build();
    }


    @Bean
    public ItemReader<? extends Member> customerItemReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<Member>()
                .name("jdbcPagingItemReader")
                .pageSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Member.class))
                .queryProvider(queryProvider())
                .build();

    }

    @Bean
    public PagingQueryProvider queryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("from member");
        //queryProvider.setWhereClause();

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
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
