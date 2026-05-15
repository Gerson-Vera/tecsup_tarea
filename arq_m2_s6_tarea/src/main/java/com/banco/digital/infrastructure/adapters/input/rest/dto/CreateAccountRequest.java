package com.banco.digital.infrastructure.adapters.input.rest.dto;

import java.math.BigDecimal;

public class CreateAccountRequest {

    private String ownerName;
    private BigDecimal initialBalance;

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
}
