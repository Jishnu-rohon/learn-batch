package com.batch.learn.service;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class SecondTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("This is a second tasklet step.");
        System.out.println("Job parameters: " + chunkContext.getStepContext().getJobParameters());
        System.out.println("Job execution context: " + chunkContext.getStepContext().getJobExecutionContext());
        return RepeatStatus.FINISHED;
    }
}