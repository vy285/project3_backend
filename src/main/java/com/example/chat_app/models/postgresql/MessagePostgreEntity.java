package com.example.chat_app.models.postgresql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class MessagePostgreEntity {
    @Id
    @Column(name = "mess_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messId;

    @Column(name = "con_id")
    private String conId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
