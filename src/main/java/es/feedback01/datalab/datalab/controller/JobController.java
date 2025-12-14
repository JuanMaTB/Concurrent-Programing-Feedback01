package es.feedback01.datalab.datalab.controller;

import es.feedback01.datalab.datalab.domain.Job;
import es.feedback01.datalab.datalab.service.JobService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // RF1: crear job
    @PostMapping
    public Job createJob() {
        return jobService.createJob();
    }

    // RF3: consultar estado del job
    @GetMapping("/{id}")
    public Job getJob(@PathVariable String id) {
        return jobService.getJob(id);
    }
}

