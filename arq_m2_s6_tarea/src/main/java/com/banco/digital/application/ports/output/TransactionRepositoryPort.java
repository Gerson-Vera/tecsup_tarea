package com.banco.digital.application.ports.output;

import com.banco.digital.domain.model.Transaction;

import java.util.List;

// Puerto de salida: contrato para persistencia de transacciones
public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(String accountId);
}
