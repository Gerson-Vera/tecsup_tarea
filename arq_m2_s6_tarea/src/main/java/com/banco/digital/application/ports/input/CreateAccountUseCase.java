package com.banco.digital.application.ports.input;

import com.banco.digital.domain.model.Account;

import java.math.BigDecimal;

// Puerto de entrada: contrato que la capa de infraestructura invoca
public interface CreateAccountUseCase {

    Account createAccount(String ownerName, BigDecimal initialBalance);
}
