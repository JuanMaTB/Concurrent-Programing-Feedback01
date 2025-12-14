package es.feedback01.datalab.datalab.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Job {

    private String id;
    private JobStatus status;
    private LocalDateTime createdAt;

    public Job() {
        this.id = UUID.randomUUID().toString();
        this.status = JobStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

