package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.utils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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

    public Page<UserInfoEntity> getByNickname(String nickname, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        long totalRecords = userInfoDao.countByNickname(nickname);
        if (totalRecords == 0) {
            return new PageImpl<>(new ArrayList<UserInfoEntity>(), pageable, totalRecords);
        } else {
            List<UserInfoEntity> entities = userInfoDao.findByNickname(nickname, pageable);
            return new PageImpl<>(entities, pageable, totalRecords);
        }
    }

    public List<UserInfoEntity> getPersonAcceptRef(long userId, String nicknameFriend) {
        return userInfoDao.findPersonAccept(userId, nicknameFriend);
    }

    public List<UserInfoEntity> getPersonWaitRef(long userId, String nicknameFriend) {
        return userInfoDao.findPersonWait(userId, nicknameFriend);
    }

    public List<UserInfoEntity> getPersonNoSend(long userId, String nicknameFriend) {
        return userInfoDao.findPersonNoSend(userId, nicknameFriend);
    }
}
