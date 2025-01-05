package com.batch.learn.controller;

import com.batch.learn.dto.JobRequestParam;
import com.batch.learn.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobLauncherController {

    @Autowired
    private JobService jobService;

    @PostMapping("/launch/{number}")
    public String handle(@PathVariable(value = "number") String number, @RequestBody List<JobRequestParam> jobRequestParams) throws Exception{
        return jobService.executeJob(number, jobRequestParams);
    }

    @GetMapping("/stop/{executionId}")
    public String handle(@PathVariable(value = "executionId") String executionId) throws Exception{
        return jobService.stopJob(executionId);
    }
}
