package com.example.chat_app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationEntity {
    String conId;

    String nameCon;

    Long userIdSendReferral; // ai gửi lời kết bạn thì người đó là userId

    Long userIdReceiveReferral; // ai g

    Long createdAt;

    Long updatedAt;

    public static String genConId(Long userIdSendReferral, Long userIdReceiveReferral) {
        return userIdSendReferral < userIdReceiveReferral ? String.format("%s-%s", userIdSendReferral, userIdReceiveReferral) : String.format("%s-%s", userIdReceiveReferral, userIdSendReferral);
    }
}
