package com.batch.learn.service;

import com.batch.learn.dto.JobRequestParam;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job firstJob;

    @Autowired
    Job secondJob;

    @Autowired
    Job flatFileJob;

    @Autowired
    private JobOperator jobOperator;

//    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    void executeScheduledJob() throws Exception {
        executeJob("1", null);
    }

    public String executeJob(String number, List<JobRequestParam> jobRequestParams) throws Exception {
        Map<String, JobParameter<?>> parameterMap = new HashMap<>();
        if(jobRequestParams != null && !jobRequestParams.isEmpty())
            jobRequestParams.forEach(param -> parameterMap.put(param.getKey(), new JobParameter<>(param.getValue(), String.class)));
        parameterMap.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));
        JobParameters jobParameters = new JobParameters(parameterMap);
        if(number.equals("1")){
//            jobOperator.startNextInstance(firstJob.getName());
            jobLauncher.run(firstJob, jobParameters);
        } else if (number.equals("2")) {
            jobLauncher.run(secondJob, jobParameters);
        } else if (number.equals("3")) {
            jobLauncher.run(flatFileJob, jobParameters);
        }
        return "Successfully executed";
    }

    public String stopJob(String executionId) {
        try {
            jobOperator.stop(Long.parseLong(executionId));
        } catch (NoSuchJobExecutionException | JobExecutionNotRunningException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "Successfully stopped";
    }
}
