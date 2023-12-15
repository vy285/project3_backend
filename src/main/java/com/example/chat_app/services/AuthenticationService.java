package com.example.chat_app.services;

import com.example.chat_app.models.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {
    public long getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountEntity accountEntity = (AccountEntity) authentication.getPrincipal();
        return accountEntity.getUserId();
    }
}
