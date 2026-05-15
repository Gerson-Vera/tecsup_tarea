package com.banco.digital.infrastructure.config;

import java.math.BigDecimal;

/**
 * PATRÓN SINGLETON — Gestiona la configuración global del banco.
 * Garantiza una única instancia en toda la JVM (thread-safe con double-checked locking).
 */
public class BankConfigurationManager {

    private static volatile BankConfigurationManager instance;

    private final String bankName;
    private final BigDecimal commissionFee;
    private final BigDecimal minimumBalance;

    private BankConfigurationManager() {
        this.bankName = "Banco Digital";
        this.commissionFee = new BigDecimal("5.00");
        this.minimumBalance = BigDecimal.ZERO;
    }

    public static BankConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (BankConfigurationManager.class) {
                if (instance == null) {
                    instance = new BankConfigurationManager();
                }
            }
        }
        return instance;
    }

    public String getBankName() { return bankName; }

    public BigDecimal getCommissionFee() { return commissionFee; }

    public BigDecimal getMinimumBalance() { return minimumBalance; }
}
