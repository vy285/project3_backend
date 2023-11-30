package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.AccountDao;
import com.example.chat_app.models.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountDao accountDao;

    @Override
    public AccountEntity loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<AccountEntity> entityOptional = accountDao.getByUserId(Long.valueOf(userId));
        if (entityOptional.isEmpty()) {
            return null;
        }
        return entityOptional.get();
    }

    public AccountEntity getbyGmail(String gmail) {
        Optional<AccountEntity> entityOptional = accountDao.getByGmail(gmail);
        if (entityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find account");
        }
        return entityOptional.get();
    }
}
