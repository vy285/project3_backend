package com.example.chat_app.models.postgresql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conversation")
public class ConversationPostgreEntity {
    @Id
    @Column(name = "con_id")
    private String conId;

    @Column(name = "name_con")
    private String nameCon;

    @Column(name = "user_id_send_referral")
    private Long userIdSendReferral;

    @Column(name = "user_id_receive_referral")
    private Long userIdReceiveReferral;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    private String genConId(Long userIdSendReferral, Long userIdReceiveReferral) {
        return userIdSendReferral < userIdReceiveReferral ? String.format("%s-%s", userIdSendReferral, userIdReceiveReferral) : String.format("%s-%s", userIdReceiveReferral, userIdSendReferral);
    }
}
