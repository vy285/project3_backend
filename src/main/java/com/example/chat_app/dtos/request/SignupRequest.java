package com.example.chat_app.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String gmail;
    private String password;
    private String nickname;
    private String code;
}
