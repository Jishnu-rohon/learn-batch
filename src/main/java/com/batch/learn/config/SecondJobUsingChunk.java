package com.batch.learn.config;

import com.batch.learn.listener.FirstItemReaderListener;
import com.batch.learn.processor.FirstItemProcessor;
import com.batch.learn.reader.FirstItemReader;
import com.batch.learn.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SecondJobUsingChunk {
    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Autowired
    private FirstItemReaderListener firstItemReaderListener;

    @Bean
    public Job secondJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Second Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstChunkedStep(jobRepository, transactionManager))
                .build();

    }

    @Bean
    public Step firstChunkedStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("First chunked step", jobRepository)
                .<Integer, Long>chunk(3, transactionManager)
                .reader(firstItemReader)
                .listener(firstItemReaderListener)
                .processor(firstItemProcessor) // this processor is optional but neither reader nor writer can be null
                .writer(firstItemWriter)
                .build();
    }

}
