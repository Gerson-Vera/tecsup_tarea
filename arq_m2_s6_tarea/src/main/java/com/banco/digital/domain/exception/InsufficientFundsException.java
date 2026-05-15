package com.banco.digital.domain.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String accountId, BigDecimal balance, BigDecimal required) {
        super(String.format(
            "Saldo insuficiente en cuenta %s. Saldo disponible: %.2f, Requerido: %.2f",
            accountId, balance, required
        ));
    }
}
