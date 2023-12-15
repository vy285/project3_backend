package com.example.chat_app.daos.impls;

import com.example.chat_app.daos.impls.repository.AccountRepository;
import com.example.chat_app.daos.interfaces.AccountDao;
import com.example.chat_app.models.AccountEntity;
import com.example.chat_app.models.postgresql.AccountPostgreEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accountDao")
@Slf4j
public class AccountImpl implements AccountDao {
    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<AccountEntity> getByUserId(Long userId) {
        Optional<AccountPostgreEntity> postgreEntityOptional = repository.findByUserId(userId);
        return postgreEntityOptional.map(postgreEntity -> mapper.map(postgreEntity, AccountEntity.class));
    }

    @Override
    public Optional<AccountEntity> getByGmail(String gmail) {
        Optional<AccountPostgreEntity> postgreEntityOptional = repository.findByGmail(gmail);
        return postgreEntityOptional.map(postgreEntity -> mapper.map(postgreEntity, AccountEntity.class));
    }

    @Override
    public Integer updatePassword(String password, Long updatedAt, Long userId) {
        return repository.updatePassword(password, updatedAt, userId);
    }

    @Override
    public AccountEntity saveEntity(AccountEntity accountEntity) {
        log.info("Saving account entity");
        AccountPostgreEntity postgreEntity = mapper.map(accountEntity, AccountPostgreEntity.class);
        postgreEntity = repository.save(postgreEntity);
        return mapper.map(postgreEntity, AccountEntity.class);
    }
}
