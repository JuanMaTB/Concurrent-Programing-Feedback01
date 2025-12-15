package es.feedback01.datalab.datalab.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobResult {

    private int taskIndex;
    private int processedRows;
    private double totalAmount;
}
