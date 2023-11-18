package com.example.chat_app.models;

import com.example.chat_app.utils.MessageStatus;
import com.example.chat_app.utils.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {
    Long messId;

    Long conId;

    Long senderId;

    Long receiverId;

    Boolean isRead;

    String content;

    MessageStatus status;

    MessageType type;

    Long createdAt;

    Long updatedAt;

}
