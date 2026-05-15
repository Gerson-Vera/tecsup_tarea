package com.banco.digital.application.usecases;

import com.banco.digital.application.ports.input.TransferMoneyUseCase;
import com.banco.digital.application.ports.output.AccountRepositoryPort;
import com.banco.digital.application.ports.output.NotificationPort;
import com.banco.digital.application.ports.output.TransactionRepositoryPort;
import com.banco.digital.domain.exception.AccountNotFoundException;
import com.banco.digital.domain.model.Account;
import com.banco.digital.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

// La comisión se inyecta desde infraestructura para que application no dependa de ella
public class TransferMoneyService implements TransferMoneyUseCase {

    private final AccountRepositoryPort accountRepository;
    private final TransactionRepositoryPort transactionRepository;
    private final NotificationPort notificationPort;
    private final BigDecimal commissionFee;

    public TransferMoneyService(AccountRepositoryPort accountRepository,
                                TransactionRepositoryPort transactionRepository,
                                NotificationPort notificationPort,
                                BigDecimal commissionFee) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.notificationPort = notificationPort;
        this.commissionFee = commissionFee;
    }

    @Override
    public Transaction transfer(String sourceAccountId, String destinationAccountId,
                                BigDecimal amount, String description) {
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser iguales");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException(sourceAccountId));
        Account destination = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new AccountNotFoundException(destinationAccountId));

        BigDecimal totalDebit = amount.add(commissionFee);

        // La validación de fondos vive en la entidad de dominio (Account.debit)
        source.debit(totalDebit);
        destination.credit(amount);

        accountRepository.save(source);
        accountRepository.save(destination);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                sourceAccountId,
                destinationAccountId,
                amount,
                commissionFee,
                description
        );
        transaction.markCompleted();

        Transaction saved = transactionRepository.save(transaction);

        notificationPort.notifyTransfer(source, destination, saved);

        return saved;
    }
}
