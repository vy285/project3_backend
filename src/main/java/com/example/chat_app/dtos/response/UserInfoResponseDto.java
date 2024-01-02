package com.example.chat_app.dtos.response;

import com.example.chat_app.utils.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {
    Long userId;

    String avatar;

    String nickname;

    String dateOfBirth;

    String status;

    String address;

    Long createdAt;

    Long updatedAt;

    String statusReferral;

    Long referralId;
}
