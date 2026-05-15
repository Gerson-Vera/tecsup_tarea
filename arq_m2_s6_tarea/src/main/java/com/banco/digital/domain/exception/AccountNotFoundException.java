package com.banco.digital.domain.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountId) {
        super("Cuenta no encontrada: " + accountId);
    }
}
