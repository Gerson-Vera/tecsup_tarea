package com.banco.digital.infrastructure.adapters.output.persistence.jpa;

import com.banco.digital.infrastructure.adapters.output.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {
}
