package com.example.chat_app.models;

import com.example.chat_app.utils.ReferralStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferralEntity {
    Long referralId;

    Long senderId;

    Long receiverId;

    ReferralStatus status;

    Long createdAt;

    Long updatedAt;
}
