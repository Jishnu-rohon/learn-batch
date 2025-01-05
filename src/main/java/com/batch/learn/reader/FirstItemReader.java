package com.batch.learn.reader;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class FirstItemReader implements ItemReader<Integer> {

    private StepExecution stepExecution;
    List<Integer> items = IntStream.range(0, 10).boxed().toList();
    int i = 0;
    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Inside Item reader");
        System.out.println("Job parameters: " + stepExecution.getJobParameters().getParameters());
        System.out.println("Job Execution Id: " + stepExecution.getJobExecution().getId());
        if (i < items.size()) {
            return items.get(i++);
        }
        return null;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
