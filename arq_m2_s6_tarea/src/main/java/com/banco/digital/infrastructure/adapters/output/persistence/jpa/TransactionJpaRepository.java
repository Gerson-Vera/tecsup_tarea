package com.banco.digital.infrastructure.adapters.output.persistence.jpa;

import com.banco.digital.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findBySourceAccountIdOrDestinationAccountId(String sourceId, String destinationId);
}
