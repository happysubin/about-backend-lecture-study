package io.springbatch.springbatchlecture.part8_itemreader.flatfile;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.util.List;


@RequiredArgsConstructor
@Configuration
public class FlatFilesConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .next(step2())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(3)
                .reader(itemReader3())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List items) throws Exception {
                        System.out.println("items = " + items);
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }



//    @Bean
//    public ItemReader itemReader() {
//        FlatFileItemReader<Member> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new ClassPathResource("/customer.csv"));
//
//        DefaultLineMapper<Member> lineMapper = new DefaultLineMapper<>();
//        lineMapper.setTokenizer(new DelimitedLineTokenizer());
//        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
//
//        itemReader.setLineMapper(lineMapper);
//        itemReader.setLinesToSkip(1);
//
//        return itemReader;
//    }

    public FlatFileItemReader itemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new ClassPathResource("customer.csv"))
                .fieldSetMapper(new CustomerFieldSetMapper())
                //.targetType(Member.class)
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("name", "year", "age")
                .build();
    }


    public FlatFileItemReader itemReader2() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new ClassPathResource("customer.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("name","year","age")
                .build();
    }

    public FlatFileItemReader itemReader3() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new FileSystemResource("내 파일 절대 경로 csv, 상대 경로는 createRelative를 활용해야 하는 듯."))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .fixedLength()
                .strict(false) //false. 파싱 예외를 잡지 않겠다
                .addColumns(new Range(1,5))
                .addColumns(new Range(6,9))
                .addColumns(new Range(10,11))
                .names("name","year","age")
                .build();

    }
}
