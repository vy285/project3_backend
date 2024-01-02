package com.example.chat_app.dtos.mapper;

import com.example.chat_app.dtos.response.ConversationResponseDto;
import com.example.chat_app.models.ConversationEntity;
import com.example.chat_app.models.MessageEntity;
import com.example.chat_app.models.UserInfoEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationMapper {
    public ConversationResponseDto toConversationResponseDto(ConversationEntity entity, MessageEntity lastMess, UserInfoEntity friendInfo) {
        ConversationResponseDto dto = new ConversationResponseDto();
        dto.setConId(entity.getConId());
        dto.setUserIdReceiveReferral(entity.getUserIdReceiveReferral());
        dto.setUserIdSendReferral(entity.getUserIdSendReferral());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        long friendId = friendInfo.getUserId();
        if (lastMess == null) {
            dto.setLastMessage("Các bạn có thể liên lạc");
        } else {
            String mess = friendId == lastMess.getSenderId() ? "Friend: " + lastMess.getContent() : "You: " + lastMess.getContent();
            dto.setLastMessage(mess);
        }
        dto.setNameCon(friendInfo.getNickname());
        dto.setAvatarFriend(friendInfo.getAvatar());
        return dto;
    }

    public List<ConversationResponseDto> toConversationResponseDtos(List<ConversationEntity> entities, List<MessageEntity> lastMessList, List<UserInfoEntity> friendList) {
        List<ConversationResponseDto> dtos = new ArrayList<ConversationResponseDto>();
        for (int i = 0; i < entities.size(); i++) {
            ConversationResponseDto dto = toConversationResponseDto(entities.get(i), lastMessList.get(i), friendList.get(i));
            dtos.add(dto);
        }
        return dtos;
    }
}
