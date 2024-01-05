package com.example.chat_app.controllers;

import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.services.AuthenticationService;
import com.example.chat_app.services.ConversationService;
import com.example.chat_app.services.ReferralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "referral")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Referral Controller")
@Slf4j
public class ReferralController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ReferralService referralService;

    @Operation(summary = "Gửi lời mời kết bạn", operationId = "sendReferral")
    @GetMapping("")
    public ResponseEntity<ResponseDto<String>> sendReferral(@RequestParam("receiverId") Long receiverId) {
        Long senderId = authenticationService.getUserIdFromContext();
        referralService.addReferral(senderId, receiverId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Lời mời đã được gửi"));
    }

    @Operation(summary = "Chấp nhận lời mởi và bắt đầu trò chuyện", operationId = "acceptReferral")
    @GetMapping("/accept")
    public ResponseEntity<ResponseDto<String>> acceptReferral(@RequestParam("senderId") Long senderId) {
        Long receiverId = authenticationService.getUserIdFromContext();
        referralService.acceptReferral(senderId, receiverId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Đã chấp nhận lời mời"));
    }

    @Operation(summary = "Từ chối lời mời", operationId = "deleteReferral")
    @DeleteMapping("")
    public ResponseEntity<ResponseDto<String>> deleteReferral(@RequestParam("referralId") Long referralId) {
        log.info("DeleteReferral " + referralId);
        referralService.deleteReferral(referralId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Đã từ chối lời mời"));
    }
}
