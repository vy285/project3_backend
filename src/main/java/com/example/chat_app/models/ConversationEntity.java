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

    Long userIdFirst;

    Long userIdSecond;

    Long createdAt;

    Long updatedAt;

    private String genConId(Long userIdFirst, Long userIdSecond) {
        return String.format("%s-%s", userIdFirst, userIdSecond);
    }
}
