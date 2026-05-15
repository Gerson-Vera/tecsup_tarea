package com.banco.digital.infrastructure.adapters.output.persistence;

import com.banco.digital.application.ports.output.TransactionRepositoryPort;
import com.banco.digital.domain.model.Transaction;
import com.banco.digital.infrastructure.adapters.output.persistence.jpa.TransactionJpaRepository;
import com.banco.digital.infrastructure.adapters.output.persistence.mapper.TransactionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PATRÓN ADAPTER #2
 * Adapta TransactionRepositoryPort a H2/JPA.
 */
@Component
public class H2TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;

    public H2TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = TransactionMapper.toEntity(transaction);
        var saved = jpaRepository.save(entity);
        return TransactionMapper.toDomain(saved);
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        return jpaRepository
                .findBySourceAccountIdOrDestinationAccountId(accountId, accountId)
                .stream()
                .map(TransactionMapper::toDomain)
                .collect(Collectors.toList());
    }
}
