package com.example.chat_app.controllers;

import com.example.chat_app.dtos.request.CreateMessageRequest;
import com.example.chat_app.dtos.response.MessageResponseDto;
import com.example.chat_app.dtos.response.PageResponseDto;
import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.models.MessageEntity;
import com.example.chat_app.services.AuthenticationService;
import com.example.chat_app.services.MessageService;
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

    @PostMapping()
    public ResponseEntity<ResponseDto<String>> sendMessage(@RequestBody CreateMessageRequest request) {
        Long senderId = authenticationService.getUserIdFromContext();
        messageService.createMessage(senderId, request.getReceiverId(), request.getContent(), request.getType());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Send message successfully"));
    }

    @GetMapping("/mark-read")
    public ResponseEntity<ResponseDto<String>> markRead(@RequestParam("messageId") Long messageId, @RequestParam("conId") String conId) {
        Long receiverId = authenticationService.getUserIdFromContext();
        messageService.markRead(receiverId, conId, messageId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, "Mark read"));
    }

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
