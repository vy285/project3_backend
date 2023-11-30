package com.example.chat_app.models.postgresql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account", indexes = {
        @Index(name = "userId" , columnList = "user_id")
})
public class    AccountPostgreEntity {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name="gmail", unique = true)
    private String gmail;

    @Column(name="password")
    private String password;

    @Column(name="created_at")
    private Long createdAt;

    @Column(name="updated_at")
    private Long updatedAt;
}
