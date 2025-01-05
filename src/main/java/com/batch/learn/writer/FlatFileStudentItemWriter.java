package com.batch.learn.writer;

import com.batch.learn.dto.StudentDTO;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FlatFileStudentItemWriter implements ItemWriter<StudentDTO> {
    @Override
    public void write(Chunk<? extends StudentDTO> chunk) throws Exception {
        System.out.println("Inside Item writer");
        for (StudentDTO item : chunk.getItems()) {
            System.out.println("Inside Item writer Student row: " + item);
        }
    }
}
