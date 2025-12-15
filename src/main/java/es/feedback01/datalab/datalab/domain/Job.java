package es.feedback01.datalab.datalab.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Job {

    private String id;
    private JobStatus status;
    private LocalDateTime createdAt;

    private int totalTasks;
    private int completedTasks;
    private double progress;

    private List<JobResult> results = new ArrayList<>();
}
