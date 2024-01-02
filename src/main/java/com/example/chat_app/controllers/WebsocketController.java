package com.example.chat_app.controllers;

import com.example.chat_app.dtos.request.CreateMessageRequest;
import com.example.chat_app.dtos.response.MessageResponseDto;
import com.example.chat_app.models.MessageEntity;
import com.example.chat_app.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebsocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    MessageService messageService;

    @MessageMapping("/message")
    public void processMessage(@Payload CreateMessageRequest request) {
        log.info("Processing message " + request.getContent());
        MessageEntity entity = messageService.createMessage(request.getSenderId(), request.getReceiverId(), request.getContent(), request.getType());
        messagingTemplate.convertAndSendToUser(
                String.valueOf(entity.getReceiverId()), "/queue/messages",
                new MessageResponseDto(
                        entity.getMessId(),
                        entity.getConId(),
                        entity.getSenderId(),
                        entity.getReceiverId(),
                        entity.getIsRead(),
                        entity.getContent(),
                        entity.getStatus().toString(),
                        entity.getType().toString(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                )
        );
//        return new MessageResponseDto(
//                entity.getMessId(),
//                entity.getConId(),
//                entity.getSenderId(),
//                entity.getReceiverId(),
//                entity.getIsRead(),
//                entity.getContent(),
//                entity.getStatus().toString(),
//                entity.getType().toString(),
//                entity.getCreatedAt(),
//                entity.getUpdatedAt()
//        );
    }
}
