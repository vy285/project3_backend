package com.example.chat_app.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    Long userId;

    String token;
}
