package es.feedback01.datalab.datalab.domain;

public class JobResult {

    private int taskIndex;
    private int processedRows;
    private double totalAmount;

    public JobResult(int taskIndex, int processedRows, double totalAmount) {
        this.taskIndex = taskIndex;
        this.processedRows = processedRows;
        this.totalAmount = totalAmount;
    }

    public int getTaskIndex() { return taskIndex; }
    public int getProcessedRows() { return processedRows; }
    public double getTotalAmount() { return totalAmount; }
}
