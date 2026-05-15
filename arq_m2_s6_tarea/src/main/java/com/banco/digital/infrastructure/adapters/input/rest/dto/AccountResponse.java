package com.banco.digital.infrastructure.adapters.input.rest.dto;

import com.banco.digital.domain.model.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountResponse {

    private String id;
    private String accountNumber;
    private String ownerName;
    private BigDecimal balance;
    private String status;
    private LocalDateTime createdAt;

    public static AccountResponse from(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setOwnerName(account.getOwnerName());
        response.setBalance(account.getBalance());
        response.setStatus(account.getStatus().name());
        response.setCreatedAt(account.getCreatedAt());
        return response;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
