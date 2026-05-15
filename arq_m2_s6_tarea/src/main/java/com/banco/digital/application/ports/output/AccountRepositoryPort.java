package com.banco.digital.application.ports.output;

import com.banco.digital.domain.model.Account;

import java.util.List;
import java.util.Optional;

// Puerto de salida: contrato que la capa de dominio/aplicación requiere para persistencia
public interface AccountRepositoryPort {

    Account save(Account account);

    Optional<Account> findById(String id);

    List<Account> findAll();
}
