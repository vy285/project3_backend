package com.example.chat_app.controllers;

import com.example.chat_app.dtos.request.CreateMessageRequest;
import com.example.chat_app.dtos.response.MessageResponseDto;
import com.example.chat_app.dtos.response.PageResponseDto;
import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.models.MessageEntity;
import com.example.chat_app.services.AuthenticationService;
import com.example.chat_app.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(path = "message")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Message Controller")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    AuthenticationService authenticationService;

    @Operation(summary = "Đánh dấu tin nhăn đã được đọc", operationId = "markRead")
    @GetMapping("/mark-read")
    public ResponseEntity<ResponseDto<String>> markRead(@RequestParam("messageId") Long messageId, @RequestParam("conId") String conId) {
        Long receiverId = authenticationService.getUserIdFromContext();
        messageService.markRead(receiverId, conId, messageId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Mark read"));
    }

    @Operation(summary = "Lấy tin nhắn của cuộc trò chuyện",operationId = "getHistoryConversation")
    @GetMapping("/{conId}")
    public ResponseEntity<ResponseDto<List<MessageResponseDto>>> getHistoryMessage(
            @RequestParam("length") Long length,
            @PathVariable("conId") String conId
            ) {
//        Long myId = authenticationService.getUserIdFromContext();
        List<MessageResponseDto> dtos = messageService.getHistoryMessage(conId, length);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, dtos));
    }
}
