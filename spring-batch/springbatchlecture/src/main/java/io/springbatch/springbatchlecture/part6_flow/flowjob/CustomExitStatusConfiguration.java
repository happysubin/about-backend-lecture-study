//package io.springbatch.springbatchlecture.part6_flow;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.listener.StepExecutionListenerSupport;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class CustomExitStatusConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//
//    @Bean
//    public Job batchJob() {
//        return jobBuilderFactory.get("batchJob")
//                .start(step1())
//                    .on("FAILED")
//                .to(step2())
//                .on("PASS")
//                .stop()
//                .end()
//                .build();
//
//    }
//
//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println(">> step1 has executed");
//                    contribution.setExitStatus(ExitStatus.FAILED);
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//
//    @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("step2")
//                .tasklet((contribution, chunkContext) -> {
//                    //System.out.println(">> step2 has executed");
//                    //return RepeatStatus.FINISHED;
//                    throw new RuntimeException();
//                })
//                .listener(new PassCheckingListener())
//                .build();
//    }
//
//    static class PassCheckingListener extends StepExecutionListenerSupport {
//        @Override
//        public ExitStatus afterStep(StepExecution stepExecution) {
//            String exitCode = stepExecution.getExitStatus().getExitCode();
//            if(exitCode.equals(ExitStatus.FAILED.getExitCode())){
//                return new ExitStatus("PASS");
//            }
//            return null;
//        }
//    }
//}
