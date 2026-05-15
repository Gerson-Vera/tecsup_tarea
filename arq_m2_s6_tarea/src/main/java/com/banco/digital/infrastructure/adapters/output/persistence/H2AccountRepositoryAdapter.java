package com.banco.digital.infrastructure.adapters.output.persistence;

import com.banco.digital.application.ports.output.AccountRepositoryPort;
import com.banco.digital.domain.model.Account;
import com.banco.digital.infrastructure.adapters.output.persistence.jpa.AccountJpaRepository;
import com.banco.digital.infrastructure.adapters.output.persistence.mapper.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PATRÓN ADAPTER #1
 * Adapta el puerto de salida AccountRepositoryPort a la implementación concreta con H2/JPA.
 * El dominio solo conoce la interfaz AccountRepositoryPort, no este adaptador.
 */
@Component
public class H2AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;

    public H2AccountRepositoryAdapter(AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account) {
        var entity = AccountMapper.toEntity(account);
        var saved = jpaRepository.save(entity);
        return AccountMapper.toDomain(saved);
    }

    @Override
    public Optional<Account> findById(String id) {
        return jpaRepository.findById(id).map(AccountMapper::toDomain);
    }

    @Override
    public List<Account> findAll() {
        return jpaRepository.findAll().stream()
                .map(AccountMapper::toDomain)
                .collect(Collectors.toList());
    }
}
