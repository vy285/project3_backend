package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;

    public void changeStatus(UserStatus status, Long userId) {
        int count_change = userInfoDao.changeStatus(status, userId);
        if (count_change == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId không tồn tại");
        }
    }
}
