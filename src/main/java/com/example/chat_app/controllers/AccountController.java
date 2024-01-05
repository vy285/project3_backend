package com.example.chat_app.controllers;

import com.example.chat_app.dtos.request.AccountRequest;
import com.example.chat_app.dtos.request.ChangePassRequest;
import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.filter.JwtTokenProvider;
import com.example.chat_app.models.AccountEntity;
import com.example.chat_app.services.AccountService;
import com.example.chat_app.services.AuthenticationService;
import com.example.chat_app.services.UserInfoService;
import com.example.chat_app.utils.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping(path = "account")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Account Controller")
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Thay đổi mật khẩu", operationId = "changePassword")
    @PostMapping("/password")
    public ResponseEntity<ResponseDto<String>> changePassword(@RequestBody ChangePassRequest request) {
        long userId = authenticationService.getUserIdFromContext();
        accountService.changePassword(request.getOldPass(), request.getNewPass(), userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Thay đổi mật khẩu thành công"));
    }

}
