package com.banco.digital.application.usecases;

import com.banco.digital.application.ports.input.CreateAccountUseCase;
import com.banco.digital.application.ports.output.AccountRepositoryPort;
import com.banco.digital.domain.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

// Servicio de aplicación: implementa el caso de uso sin depender de frameworks
public class CreateAccountService implements CreateAccountUseCase {

    private final AccountRepositoryPort accountRepository;

    public CreateAccountService(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String ownerName, BigDecimal initialBalance) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("El nombre del titular es obligatorio");
        }
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }

        String id = UUID.randomUUID().toString();
        String accountNumber = generateAccountNumber();
        Account account = new Account(id, accountNumber, ownerName, initialBalance);

        return accountRepository.save(account);
    }

    private String generateAccountNumber() {
        long number = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return "0001-" + number;
    }
}
