package com.batch.learn;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableBatchProcessing
@EnableAsync
@EnableScheduling
public class LearnBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnBatchApplication.class, args);
	}

}
