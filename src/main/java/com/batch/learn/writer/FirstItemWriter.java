package com.batch.learn.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<Long> {
    @Override
    public void write(Chunk<? extends Long> chunk) throws Exception {

        for (Long item : chunk.getItems()) {
            System.out.println("Inside Item writer: " + item);
        }
    }
}
