package com.example.chat_app.dtos.response;

import com.example.chat_app.utils.MessageStatus;
import com.example.chat_app.utils.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    Long messId;

    String conId;

    Long senderId;

    Long receiverId;

    Boolean isRead;

    String content;

    String status;

    String type;

    Long createdAt;

    Long updatedAt;
}
