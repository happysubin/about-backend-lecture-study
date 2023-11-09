package io.springbatch.springbatchlecture.part10_itemprocessor;

import io.springbatch.springbatchlecture.part9_itemwriter.flatfile.Customer;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Proc;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ItemProcessorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob@123")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .build();

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(10)
                .reader(itemReader1())
                .processor(compositeItemProcessor())
                .writer(customWriter1())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemProcessor<String, String> compositeItemProcessor() {
        List list = new ArrayList<>();
        list.add(new CustomItemProcessor1());
        list.add(new CustomItemProcessor2());
        return new CompositeItemProcessorBuilder<>() //체이닝 방식으로 진행됨
                .delegates(list)
                .build();
    }

    @Bean
    public ItemWriter<String> customWriter1() {
        return (items) -> {
            for (String item : items) {
                System.out.println("item = " + item);
            }
        };
    }


    @Bean
    public ItemReader<String> itemReader1() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("name");
        }
        return new ListItemReader<>(list);
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<ProcessorInfo, ProcessorInfo>chunk(10)
                .reader(new ItemReader<ProcessorInfo>() {

                    int i = 0;

                    @Override
                    public ProcessorInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        ProcessorInfo info = new ProcessorInfo(i);
                        return i > 3 ? null: info;
                    }
                })
                .processor(classifierCompositeItemProcessor())
                .writer(new ItemWriter<ProcessorInfo>() {
                    @Override
                    public void write(List<? extends ProcessorInfo> items) throws Exception {
                        for (ProcessorInfo item : items) {
                            System.out.println("item = " + item);
                        }
                    }
                })
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemProcessor<ProcessorInfo, ProcessorInfo> classifierCompositeItemProcessor() {
        ClassifierCompositeItemProcessor<ProcessorInfo, ProcessorInfo> processor = new ClassifierCompositeItemProcessor<>();

        ProcessorClassifier<ProcessorInfo, ItemProcessor<?,? extends ProcessorInfo>> classifier = new ProcessorClassifier<>();
        Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> map = new HashMap<>();

        map.put(1, processor1());
        map.put(2, processor2());
        map.put(3, processor3());

        classifier.setMap(map);

        processor.setClassifier(classifier);

        return processor;
    }


    public ItemProcessor<ProcessorInfo, ProcessorInfo> processor1() {
        return new ItemProcessor<ProcessorInfo, ProcessorInfo>() {
            @Override
            public ProcessorInfo process(ProcessorInfo item) throws Exception {
                System.out.println("itemProcessor 1");
                return item;
            }
        };
    }

    public ItemProcessor<ProcessorInfo, ProcessorInfo> processor2() {
        return new ItemProcessor<ProcessorInfo, ProcessorInfo>() {
            @Override
            public ProcessorInfo process(ProcessorInfo item) throws Exception {
                System.out.println("itemProcessor 2");
                return item;
            }
        };
    }

    public ItemProcessor<ProcessorInfo, ProcessorInfo> processor3() {
        return new ItemProcessor<ProcessorInfo, ProcessorInfo>() {
            @Override
            public ProcessorInfo process(ProcessorInfo item) throws Exception {
                System.out.println("itemProcessor 3");
                return item;
            }
        };
    }
}
