package com.example.chat_app.controllers;

import com.example.chat_app.dtos.request.AccountRequest;
import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.filter.JwtTokenProvider;
import com.example.chat_app.models.AccountEntity;
import com.example.chat_app.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping(path = "public/account")
@RestController
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody AccountRequest request) {
        AccountEntity entity = accountService.getbyGmail(request.getGmail());
        if(request.getPassword().equals(entity.getPassword())) {
            String jwtToken = new JwtTokenProvider().generateToken(entity);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>("ok", jwtToken));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password incorrect");
        }
    }

    @GetMapping("/")
    public String hello() {
        AccountEntity a = new AccountEntity();
        a.setUserId(10L);
        return new JwtTokenProvider().generateToken(a);
    }
}
