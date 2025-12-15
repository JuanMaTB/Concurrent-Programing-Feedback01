package es.feedback01.datalab.datalab.controller;

import es.feedback01.datalab.datalab.domain.Job;
import es.feedback01.datalab.datalab.domain.JobResultsPage;
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
    // rf2: al crearlo, ya lanza el procesado en paralelo
    @PostMapping
    public Job createJob() {
        return jobService.createJob();
    }

    // RF3: consultar job
    @GetMapping("/{id}")
    public Job getJob(@PathVariable String id) {
        return jobService.getJob(id);
    }

    // RF4: resultados paginados
    @GetMapping("/{id}/results")
    public JobResultsPage getResults(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return jobService.getResults(id, page, size);
    }
}
