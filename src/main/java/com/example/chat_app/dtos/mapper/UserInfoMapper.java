package com.example.chat_app.dtos.mapper;

import com.example.chat_app.daos.interfaces.ReferralDao;
import com.example.chat_app.dtos.response.UserInfoResponseDto;
import com.example.chat_app.models.ReferralEntity;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.utils.ReferralStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoMapper {
    @Autowired
    ReferralDao referralDao;

    public UserInfoResponseDto statusOf(UserInfoEntity entity, long myId) {
        ReferralEntity referralEntity = referralDao.statusOfReferral(myId, entity.getUserId());
        UserInfoResponseDto dto = new UserInfoResponseDto();
        dto.setUserId(entity.getUserId());
        dto.setStatus(entity.getStatus().toString());
        dto.setAddress(entity.getAddress());
        dto.setAvatar(entity.getAvatar());
        dto.setNickname(entity.getNickname());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        //chua gui ket ban
        if (referralEntity == null) {
            dto.setStatusReferral("NO");
            dto.setReferralId(null);
        }
        else {
            dto.setReferralId(referralEntity.getReferralId());
            if(referralEntity.getStatus() == ReferralStatus.YES) dto.setStatusReferral("YES");
            if(referralEntity.getStatus() == ReferralStatus.WAIT && myId == referralEntity.getSenderId()) dto.setStatusReferral("WAIT");
            if(referralEntity.getStatus() == ReferralStatus.WAIT && myId != referralEntity.getSenderId()) dto.setStatusReferral("NORELY");
        }
        return dto;
    }

    public List<UserInfoResponseDto> getUserInfoResponseDtos(List<UserInfoEntity> entities, long myId) {
        List<UserInfoResponseDto> dtos = new ArrayList<UserInfoResponseDto>();
        for (UserInfoEntity entity : entities){
            UserInfoResponseDto dto = statusOf(entity, myId);
            dtos.add(dto);
        }
        return dtos;
    }
}
