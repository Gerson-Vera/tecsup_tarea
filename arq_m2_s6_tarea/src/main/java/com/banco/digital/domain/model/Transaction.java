package com.banco.digital.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Entidad de dominio pura: representa el registro de una transferencia
public class Transaction {

    private String id;
    private String sourceAccountId;
    private String destinationAccountId;
    private BigDecimal amount;
    private BigDecimal commission;
    private TransactionStatus status;
    private String description;
    private LocalDateTime createdAt;

    public Transaction() {}

    public Transaction(String id, String sourceAccountId, String destinationAccountId,
                       BigDecimal amount, BigDecimal commission, String description) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.commission = commission;
        this.description = description;
        this.status = TransactionStatus.PENDIENTE;
        this.createdAt = LocalDateTime.now();
    }

    public void markCompleted() {
        this.status = TransactionStatus.COMPLETADA;
    }

    public void markFailed() {
        this.status = TransactionStatus.FALLIDA;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSourceAccountId() { return sourceAccountId; }
    public void setSourceAccountId(String sourceAccountId) { this.sourceAccountId = sourceAccountId; }

    public String getDestinationAccountId() { return destinationAccountId; }
    public void setDestinationAccountId(String destinationAccountId) { this.destinationAccountId = destinationAccountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getCommission() { return commission; }
    public void setCommission(BigDecimal commission) { this.commission = commission; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
