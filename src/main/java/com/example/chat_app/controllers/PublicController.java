package com.example.chat_app.controllers;

import com.example.chat_app.dtos.request.AccountRequest;
import com.example.chat_app.dtos.request.SignupRequest;
import com.example.chat_app.dtos.request.VerifyCodeRequest;
import com.example.chat_app.dtos.response.LoginResponse;
import com.example.chat_app.dtos.response.PageResponseDto;
import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.filter.JwtTokenProvider;
import com.example.chat_app.models.AccountEntity;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.services.AccountService;
import com.example.chat_app.services.UserInfoService;
import com.example.chat_app.utils.UserStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping(path = "public")
@RestController
@Tag(name = "Public Controller")
@Slf4j
public class PublicController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(value = "/forgot-password/{email}")
    public ResponseEntity<ResponseDto<String>> getPassword(@PathVariable(name = "email") String email) {
//        AccountEntity accountEntity = accountService.getbyGmail(email);
        log.info("email: " + email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vykroos285@gmail.com");
        message.setTo(email);
        message.setSubject("Get Password");
//        message.setText("Yourpassword is: " + accountEntity.getPassword());
        message.setText("lay lai mk");
        javaMailSender.send(message);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Success"));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@RequestBody AccountRequest request) {
        AccountEntity entity = accountService.getbyGmail(request.getGmail());
        if (request.getPassword().equals(entity.getPassword())) {
            String jwtToken = new JwtTokenProvider().generateToken(entity);
            LoginResponse response = new LoginResponse();
            response.setUserId(entity.getUserId());
            response.setToken(jwtToken);
            userInfoService.changeStatus(UserStatus.ONLINE, entity.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, response));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password incorrect");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody SignupRequest request) {
        log.info("di vao signup");
        AccountEntity entity = accountService.getbyGmail(request.getGmail());
        if (entity.isEnabled() == true)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gmail is used before");
        String code = entity.getVerifyCode().split("_")[0];
        Long expired = Long.valueOf(entity.getVerifyCode().split("_")[1]);
        Long now = System.currentTimeMillis();
        if (request.getCode().equals(code) && now < expired) {
            entity.setPassword(request.getPassword());
            entity.setEnable(true);
            entity.setVerifyCode(null);
            accountService.verifySuccess(entity, request.getNickname());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Signup successfully"));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "VerifyCode not true");
        }
    }

    @PostMapping("/sendVerifyCode")
    public ResponseEntity<ResponseDto<String>> sendVerifyCode(@RequestBody VerifyCodeRequest request) {
        log.info("VerifyCode");
        accountService.saveVerifyCode(request.getGmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Create verifyCode successfully"));
    }

    @GetMapping("/searchUser")
    public ResponseEntity<PageResponseDto<UserInfoEntity>> getUserInfoBy(
            @RequestParam("nickname") String nickname,
            @RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
            @RequestParam(name = "size", defaultValue = "5") @Min(1) @Max(100) int size
    ) {
        Page<UserInfoEntity> userInfoEntityPage = userInfoService.getByNickname(nickname, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new PageResponseDto<>(200, userInfoEntityPage));
    }
}
