package com.banco.digital.application.usecases;

import com.banco.digital.application.ports.input.GetAccountBalanceUseCase;
import com.banco.digital.application.ports.output.AccountRepositoryPort;
import com.banco.digital.domain.exception.AccountNotFoundException;
import com.banco.digital.domain.model.Account;

import java.math.BigDecimal;
import java.util.List;

public class GetAccountBalanceService implements GetAccountBalanceUseCase {

    private final AccountRepositoryPort accountRepository;

    public GetAccountBalanceService(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    @Override
    public BigDecimal getBalance(String accountId) {
        return getAccount(accountId).getBalance();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
