package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.dtos.mapper.UserInfoMapper;
import com.example.chat_app.dtos.response.ProfileResponseDto;
import com.example.chat_app.dtos.response.UserInfoResponseDto;
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

    @Autowired
    UserInfoMapper mapper;

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

    public List<UserInfoResponseDto> search(long myId, String nickname) {
        List<UserInfoEntity> entities = userInfoDao.findBy(nickname, myId);
        return mapper.getUserInfoResponseDtos(entities, myId);
    }

    public List<UserInfoResponseDto> searchWaitReferral(long myId) {
        return userInfoDao.findWaitReferral(myId);
    }

    public ProfileResponseDto getProfile(long myId) {
        UserInfoEntity entity = userInfoDao.findById(myId);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token không hợp lệ");
        }
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setUserId(myId);
        dto.setAvatar(entity.getAvatar());
        dto.setAddress(entity.getAddress());
        dto.setNickname(entity.getNickname());
        dto.setStatus(entity.getStatus().toString());
        dto.setDateOfBirth(String.valueOf(entity.getDateOfBirth()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;

    }

    public void updateProfile(long myId, String avatar, String nickname, String address, String dateOfBirth) {
        Long now = System.currentTimeMillis();
        UserInfoEntity entity = new UserInfoEntity();
        entity.setUserId(myId);
        entity.setAvatar(avatar);
        entity.setAddress(address);
        entity.setNickname(nickname);
        entity.setDateOfBirth(dateOfBirth);
        entity.setUpdatedAt(now);

        int countUpdate = userInfoDao.updateUser(entity);
        if(countUpdate == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể cập nhập");
    }
}
