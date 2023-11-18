package com.example.chat_app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    Long userId;

    String gmail;

    String password;

    Long createdAt;

    Long updatedAt;
}
