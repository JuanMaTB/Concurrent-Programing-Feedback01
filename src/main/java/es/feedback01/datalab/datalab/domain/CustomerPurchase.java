package es.feedback01.datalab.datalab.domain;

import java.time.LocalDateTime;

// esta clase representa una compra hecha por un cliente
// basicamente es una fila del csv pasada a java
public class CustomerPurchase {

    private Long id;
    private Long customerId;
    private String product;
    private String category;
    private Double amount;
    private LocalDateTime timestamp;
    private String paymentType;
    private String region;

    // constructor vacio para poder crear el objeto y luego rellenarlo
    public CustomerPurchase() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
