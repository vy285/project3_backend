package com.example.chat_app.daos.interfaces;

import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.utils.UserStatus;

public interface UserInfoDao {
    public int changeStatus(UserStatus status, Long userId);

    public void insertEntity(UserInfoEntity userInfo);
}
