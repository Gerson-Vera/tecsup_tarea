package com.banco.digital.infrastructure.adapters.output.persistence.mapper;

import com.banco.digital.domain.model.Transaction;
import com.banco.digital.domain.model.TransactionStatus;
import com.banco.digital.infrastructure.adapters.output.persistence.entity.TransactionEntity;

public class TransactionMapper {

    public static TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setSourceAccountId(transaction.getSourceAccountId());
        entity.setDestinationAccountId(transaction.getDestinationAccountId());
        entity.setAmount(transaction.getAmount());
        entity.setCommission(transaction.getCommission());
        entity.setStatus(transaction.getStatus().name());
        entity.setDescription(transaction.getDescription());
        entity.setCreatedAt(transaction.getCreatedAt());
        return entity;
    }

    public static Transaction toDomain(TransactionEntity entity) {
        Transaction transaction = new Transaction();
        transaction.setId(entity.getId());
        transaction.setSourceAccountId(entity.getSourceAccountId());
        transaction.setDestinationAccountId(entity.getDestinationAccountId());
        transaction.setAmount(entity.getAmount());
        transaction.setCommission(entity.getCommission());
        transaction.setStatus(TransactionStatus.valueOf(entity.getStatus()));
        transaction.setDescription(entity.getDescription());
        transaction.setCreatedAt(entity.getCreatedAt());
        return transaction;
    }
}
