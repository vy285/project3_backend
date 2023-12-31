package com.example.chat_app.controllers;

import com.example.chat_app.dtos.response.ConversationResponseDto;
import com.example.chat_app.dtos.response.PageResponseDto;
import com.example.chat_app.dtos.response.ResponseDto;
import com.example.chat_app.models.ConversationEntity;
import com.example.chat_app.services.AuthenticationService;
import com.example.chat_app.services.ConversationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(path = "conversation")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Conversation Controller")
@Slf4j
public class ConversationController {
    @Autowired
    ConversationService conversationService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<ConversationResponseDto>>> getLastConversation(
    ) {
        Long userId = authenticationService.getUserIdFromContext();
         List<ConversationResponseDto> dtos = conversationService.getConversationRecent(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(200, dtos));
    }
}
