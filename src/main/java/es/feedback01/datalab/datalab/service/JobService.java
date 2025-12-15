package es.feedback01.datalab.datalab.service;

import es.feedback01.datalab.datalab.domain.Job;
import es.feedback01.datalab.datalab.domain.JobResult;
import es.feedback01.datalab.datalab.domain.JobResultsPage;
import es.feedback01.datalab.datalab.domain.JobStatus;
import es.feedback01.datalab.datalab.domain.CustomerPurchase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class JobService {

    // almacenamos los jobs en memoria (suficiente para el feedback)
    private final Map<String, Job> jobs = new ConcurrentHashMap<>();

    // esto es lo que mola en spring: inyeccion, nada de metodos estaticos raros
    private final CustomerPurchaseService purchaseService;

    // pool configurable "a lo basico": 4 hilos fijos
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public JobService(CustomerPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    // RF1 + RF2: crear job y lanzar procesamiento (tasks en paralelo)
    public Job createJob() {

        // creamos el job
        Job job = new Job();
        job.setId(UUID.randomUUID().toString());
        job.setStatus(JobStatus.PENDING);

        jobs.put(job.getId(), job);

        // lo ponemos en running para que se vea en el GET
        job.setStatus(JobStatus.RUNNING);

        // cogemos el dataset ya cargado en memoria (el csv)
        List<CustomerPurchase> all = purchaseService.getPurchases();

        // dividimos en N fragmentos (aqui lo dejo fijo a 4, simple)
        int taskCount = 4;
        int chunkSize = (int) Math.ceil(all.size() / (double) taskCount);

        job.setTotalTasks(taskCount);
        job.setCompletedTasks(0);
        job.setProgress(0.0);
        job.setResults(new ArrayList<>());

        List<Future<JobResult>> futures = new ArrayList<>();

        for (int i = 0; i < taskCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, all.size());
            int taskIndex = i;

            // si el ultimo chunk queda vacio, lo saltamos
            if (start >= end) {
                continue;
            }

            List<CustomerPurchase> fragment = all.subList(start, end);

            Future<JobResult> f = executor.submit(() -> {

                // delay simple para que se vea RUNNING (no es pro, es para el feedback)
                try {
                    Thread.sleep(400);
                } catch (InterruptedException ignored) {}

                double totalAmount = 0.0;

                for (CustomerPurchase p : fragment) {
                    // lo de las comas: algunos numeros vienen con coma, y double.parseDouble peta
                    // solucion basica: cambio coma por punto y tiro millas
                    totalAmount += safeDouble(p.getAmount());
                }

                return new JobResult(taskIndex, fragment.size(), totalAmount);

            });

            futures.add(f);
        }

        // juntamos resultados (esperamos a que terminen)
        int completed = 0;
        try {
            for (Future<JobResult> f : futures) {
                JobResult result = f.get();
                job.getResults().add(result);

                completed++;
                job.setCompletedTasks(completed);
                job.setProgress((completed * 100.0) / job.getTotalTasks());
            }

            job.setStatus(JobStatus.COMPLETED);

        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
        }

        return job;
    }

    // RF3: consultar estado del job
    public Job getJob(String id) {
        return jobs.get(id);
    }

    // RF4: listar resultados paginados
    public JobResultsPage getResults(String jobId, int page, int size) {
        Job job = jobs.get(jobId);
        if (job == null) {
            return new JobResultsPage(jobId, 0, page, size, List.of());
        }

        List<JobResult> all = job.getResults();
        int total = all.size();

        int from = Math.max(0, page * size);
        int to = Math.min(total, from + size);

        List<JobResult> content = (from >= to) ? List.of() : all.subList(from, to);

        return new JobResultsPage(jobId, total, page, size, content);
    }

    // helper ultra simple para evitar problemas con comas o nulls
    private double safeDouble(Double value) {
        if (value == null) return 0.0;
        return value;
    }
}
