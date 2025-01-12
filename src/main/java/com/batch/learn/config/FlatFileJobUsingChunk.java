package com.batch.learn.config;

import com.batch.learn.dto.StudentDTO;
import com.batch.learn.writer.FlatFileStudentItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FlatFileJobUsingChunk {

    @Autowired
    FlatFileStudentItemWriter flatFileStudentItemWriter;

    @Bean
    public Job flatFileJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Flat file Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(flatFileStep(jobRepository, transactionManager))
                .build();

    }

    public Step flatFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Flat file step", jobRepository)
                .<StudentDTO, StudentDTO>chunk(3, transactionManager)
                .reader(flatFileItemReader())
//                .processor(firstItemProcessor)
                .writer(flatFileStudentItemWriter)
                .build();
    }

    public FlatFileItemReader<StudentDTO> flatFileItemReader() {
        FlatFileItemReader<StudentDTO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("flatfile/students.csv"));
        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentDTO>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(StudentDTO.class);
                    }
                });
            }
        });
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }
}
