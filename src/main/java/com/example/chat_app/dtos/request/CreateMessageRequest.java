package com.example.chat_app.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageRequest {
    String content;
    String type;
    Long senderId;
    Long receiverId;
}
