package com.example.chat_app.models;

import com.example.chat_app.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoEntity {
    Long userId;

    String avatar;

    String nickname;

    Date dateOfBirth;

    UserStatus status;

    String address;

    Long createdAt;

    Long updatedAt;
}
