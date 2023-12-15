package com.example.chat_app.daos.interfaces;

import com.example.chat_app.models.AccountEntity;

import java.util.Optional;

public interface AccountDao {
    public Optional<AccountEntity> getByUserId(Long userId);

    public Optional<AccountEntity> getByGmail(String gmail);

    public Integer updatePassword(String password, Long updatedAt, Long userId);

    public AccountEntity saveEntity(AccountEntity accountEntity);
}
