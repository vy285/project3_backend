package com.example.chat_app.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponseDto {
    String conId;

    String nameCon;

    Long userIdSendReferral; // ai gửi lời kết bạn thì người đó là userId

    Long userIdReceiveReferral; // ai g

    String avatarFriend;

    Long createdAt;

    Long updatedAt;

    String lastMessage;
}
