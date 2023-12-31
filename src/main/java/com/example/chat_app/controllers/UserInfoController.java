package com.example.chat_app.controllers;

import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.services.AuthenticationService;
import com.example.chat_app.services.UserInfoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "userInfo")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "UserInfo Controller")
@Slf4j
public class UserInfoController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/getAcceptRef")
    public ResponseEntity<ResponseDto<List<UserInfoEntity>>> getPersonAcceptRef(@RequestParam("nickname") String nickname) {
        long userId = authenticationService.getUserIdFromContext();
        List<UserInfoEntity> entities = userInfoService.getPersonAcceptRef(userId, nickname);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, entities));
    }

    @GetMapping("/getWaitRef")
    public ResponseEntity<ResponseDto<List<UserInfoEntity>>> getPersonWaitRef(@RequestParam("nickname") String nickname) {
        long userId = authenticationService.getUserIdFromContext();
        List<UserInfoEntity> entities = userInfoService.getPersonWaitRef(userId, nickname);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, entities));
    }

    @GetMapping("/getNoRef")
    public ResponseEntity<ResponseDto<List<UserInfoEntity>>> getPersonNoRef(@RequestParam("nickname") String nickname) {
        long userId = authenticationService.getUserIdFromContext();
        List<UserInfoEntity> entities = userInfoService.getPersonNoSend(userId, nickname);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, entities));
    }
}
