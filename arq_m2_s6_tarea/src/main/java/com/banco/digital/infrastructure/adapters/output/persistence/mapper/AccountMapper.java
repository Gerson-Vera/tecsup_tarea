package com.banco.digital.infrastructure.adapters.output.persistence.mapper;

import com.banco.digital.domain.model.Account;
import com.banco.digital.domain.model.AccountStatus;
import com.banco.digital.infrastructure.adapters.output.persistence.entity.AccountEntity;

public class AccountMapper {

    public static AccountEntity toEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId());
        entity.setAccountNumber(account.getAccountNumber());
        entity.setOwnerName(account.getOwnerName());
        entity.setBalance(account.getBalance());
        entity.setStatus(account.getStatus().name());
        entity.setCreatedAt(account.getCreatedAt());
        entity.setUpdatedAt(account.getUpdatedAt());
        return entity;
    }

    public static Account toDomain(AccountEntity entity) {
        Account account = new Account();
        account.setId(entity.getId());
        account.setAccountNumber(entity.getAccountNumber());
        account.setOwnerName(entity.getOwnerName());
        account.setBalance(entity.getBalance());
        account.setStatus(AccountStatus.valueOf(entity.getStatus()));
        account.setCreatedAt(entity.getCreatedAt());
        account.setUpdatedAt(entity.getUpdatedAt());
        return account;
    }
}
