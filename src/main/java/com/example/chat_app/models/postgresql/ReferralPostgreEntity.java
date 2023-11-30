package com.example.chat_app.models.postgresql;

import com.example.chat_app.utils.ReferralStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "referral")
public class ReferralPostgreEntity {
    @Id
    @Column(name = "referral_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long referralId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
