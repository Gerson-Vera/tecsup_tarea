package com.banco.digital.application.ports.input;

import com.banco.digital.domain.model.Transaction;

import java.math.BigDecimal;

// Puerto de entrada: caso de uso principal del sistema
public interface TransferMoneyUseCase {

    Transaction transfer(String sourceAccountId, String destinationAccountId, BigDecimal amount, String description);
}
