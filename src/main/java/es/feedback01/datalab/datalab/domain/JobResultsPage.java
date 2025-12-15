package es.feedback01.datalab.datalab.domain;

import java.util.List;

// clase simple para devolver resultados paginados del job
// no es nada fancy, solo un contenedor para cumplir el rf4
public class JobResultsPage {

    private String jobId;
    private int total;
    private int page;
    private int size;
    private List<JobResult> content;

    public JobResultsPage(String jobId, int total, int page, int size, List<JobResult> content) {
        this.jobId = jobId;
        this.total = total;
        this.page = page;
        this.size = size;
        this.content = content;
    }

    public String getJobId() {
        return jobId;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<JobResult> getContent() {
        return content;
    }
}
