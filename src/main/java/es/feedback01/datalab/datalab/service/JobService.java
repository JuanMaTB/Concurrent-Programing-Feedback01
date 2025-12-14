package es.feedback01.datalab.datalab.service;

import es.feedback01.datalab.datalab.domain.Job;
import es.feedback01.datalab.datalab.domain.JobStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JobService {

    // almacenamos los jobs en memoria (suficiente para el feedback)
    private final Map<String, Job> jobs = new ConcurrentHashMap<>();

    public Job createJob() {
        Job job = new Job();
        jobs.put(job.getId(), job);
        return job;
    }

    public Job getJob(String id) {
        return jobs.get(id);
    }

    public void updateStatus(String id, JobStatus status) {
        Job job = jobs.get(id);
        if (job != null) {
            job.setStatus(status);
        }
    }
}
