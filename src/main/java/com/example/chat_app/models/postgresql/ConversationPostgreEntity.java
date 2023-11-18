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

    @Column(name = "user_id_first")
    private Long userIdFirst;

    @Column(name = "user_id_second")
    private Long userIdSecond;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    private String genConId(Long userIdFirst, Long userIdSecond) {
        return String.format("%s-%s", userIdFirst, userIdSecond);
    }

}
