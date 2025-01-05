package com.batch.learn.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<Integer, Long> {
    @Override
    public Long process(Integer item) throws Exception {
        System.out.println("Inside Item processor: " + item);
        Thread.sleep(5000); // To simulate a long running process to test stop a running job
        return Long.valueOf(item) + 20;
    }
}
