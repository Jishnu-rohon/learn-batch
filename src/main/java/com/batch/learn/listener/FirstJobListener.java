package com.batch.learn.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before first job: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job parameters: " + jobExecution.getJobParameters());
        System.out.println("Job Execution context: " + jobExecution.getExecutionContext());
        System.out.println("Job Execution Id: " + jobExecution.getId());
        jobExecution.getExecutionContext().put("key1", "test value1");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After first job: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job parameters: " + jobExecution.getJobParameters());
        System.out.println("Job Execution context: " + jobExecution.getExecutionContext());
        System.out.println("Job Instance Id: " + jobExecution.getJobId());
        System.out.println("Job Execution Id: " + jobExecution.getId());
    }
}
