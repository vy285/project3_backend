package com.example.chat_app.dtos.mapper;

import com.example.chat_app.dtos.response.ConversationResponseDto;
import com.example.chat_app.models.ConversationEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationMapper {
    public ConversationResponseDto toConversationResponseDto(ConversationEntity entity, String lastMess,String avatarFriend) {
        ConversationResponseDto dto = new ConversationResponseDto();
        dto.setConId(entity.getConId());
        dto.setNameCon(entity.getNameCon());
        dto.setUserIdReceiveReferral(entity.getUserIdReceiveReferral());
        dto.setUserIdSendReferral(entity.getUserIdSendReferral());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setLastMessage(lastMess);
        dto.setAvatarFriend(avatarFriend);
        return dto;
    }

    public List<ConversationResponseDto> toConversationResponseDtos(List<ConversationEntity> entities, List<String> lastMessList, List<String> avatarFriends) {
        List<ConversationResponseDto> dtos = new ArrayList<ConversationResponseDto>();
        for (int i = 0; i < entities.size(); i++) {
            ConversationResponseDto dto = toConversationResponseDto(entities.get(i), lastMessList.get(i), avatarFriends.get(i));
            dtos.add(dto);
        }
        return dtos;
    }
}
