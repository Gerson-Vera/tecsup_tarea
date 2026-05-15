package com.banco.digital.application.ports.input;

import com.banco.digital.domain.model.Account;

import java.math.BigDecimal;
import java.util.List;

// Puerto de entrada: consultas de cuenta
public interface GetAccountBalanceUseCase {

    Account getAccount(String accountId);

    BigDecimal getBalance(String accountId);

    List<Account> getAllAccounts();
}
