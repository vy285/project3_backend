package com.example.chat_app.models.postgresql;

import com.example.chat_app.utils.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserPostgreEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "gmail")
    private String gmail;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "apdated_at")
    private Long updatedAt;
}
