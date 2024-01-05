package com.example.chat_app.controllers;

import com.example.chat_app.dtos.request.UpdateUserInfoRequest;
import com.example.chat_app.dtos.response.ProfileResponseDto;
import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.dtos.response.UserInfoResponseDto;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.services.AuthenticationService;
import com.example.chat_app.services.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Tìm kiếm bằng nick name", operationId = "search")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<UserInfoResponseDto>>> search(@RequestParam("nickname") String nickname) {
        long myId = authenticationService.getUserIdFromContext();
        List<UserInfoResponseDto> dtos = userInfoService.search(myId, nickname);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, dtos));
    }

//    @Operation()
    @GetMapping("/waitReferral")
    public ResponseEntity<ResponseDto<List<UserInfoResponseDto>>> searchWaitReferral() {
        long myId = authenticationService.getUserIdFromContext();
        List<UserInfoResponseDto> dtos = userInfoService.searchWaitReferral(myId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, dtos));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<ProfileResponseDto>> getProfile() {
        long myId = authenticationService.getUserIdFromContext();
        ProfileResponseDto dto = userInfoService.getProfile(myId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, dto));
    }

    @PutMapping("")
    public ResponseEntity<ResponseDto<String>> updateProfile(@RequestBody UpdateUserInfoRequest request) {
        long myId = authenticationService.getUserIdFromContext();
        userInfoService.updateProfile(myId, request.getAvatar(), request.getNickname(), request.getAddress(), request.getDateOfBirth());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "update thanh cong"));
    }
}
