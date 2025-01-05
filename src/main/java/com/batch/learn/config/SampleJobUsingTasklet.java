package com.batch.learn.config;

import com.batch.learn.listener.FirstJobListener;
import com.batch.learn.listener.FirstStepListener;
import com.batch.learn.service.SecondTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJobUsingTasklet {

    @Autowired
    private SecondTasklet secondTasklet;

    @Autowired
    private FirstJobListener firstJobListener;

    @Autowired
    private FirstStepListener firstStepListener;

    @Bean
    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("FirstJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // this will increase the run.id of job by 1 and thus the job will always run as it will be unique
                .start(firstStep(jobRepository, transactionManager))
                .next(secondStep(jobRepository, transactionManager))
                .listener(firstJobListener)
                .build();
    }

    @Bean
    public Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("FirstStep", jobRepository)
                .tasklet(firstTask(), transactionManager)
                .listener(firstStepListener)
                .build();
    }

    @Bean
    public Tasklet firstTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is a first tasklet step.");
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    public Step secondStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("SecondStep", jobRepository)
                .tasklet(secondTasklet, transactionManager)
                .build();
    }

}
