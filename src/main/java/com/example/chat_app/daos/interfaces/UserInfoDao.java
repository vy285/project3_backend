package com.example.chat_app.daos.interfaces;

import com.example.chat_app.dtos.response.UserInfoResponseDto;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.utils.UserStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserInfoDao {
    public int changeStatus(UserStatus status, Long userId);

    public void insertEntity(UserInfoEntity userInfo);

    public List<UserInfoEntity> findByNickname(String nickname, Pageable pageable);

    public long countByNickname(String nickname);

    public List<UserInfoEntity> findBy(String nickname, long myid);

    public List<UserInfoResponseDto> findWaitReferral(long myId);

    public UserInfoEntity findById(long myId);

    public Integer updateUser(UserInfoEntity entity);
}
