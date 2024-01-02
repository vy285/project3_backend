package com.example.chat_app.models.postgresql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountPostgreEntity {
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

    @Column(name = "verify_code")
    private String verifyCode;

    @Column(name = "enable", columnDefinition = "varchar(255) default 'false'")
    private boolean enable;
}
