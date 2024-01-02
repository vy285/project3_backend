package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.ConversationDao;
import com.example.chat_app.daos.interfaces.MessageDao;
import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.dtos.mapper.ConversationMapper;
import com.example.chat_app.dtos.response.ConversationResponseDto;
import com.example.chat_app.models.ConversationEntity;
import com.example.chat_app.models.MessageEntity;
import com.example.chat_app.models.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConversationService {
    @Autowired
    private ConversationDao conversationDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private ConversationMapper conversationMapper;

    public void createConversation(Long senderId, Long receiverId, String nicknameFriend) {
        Long now = System.currentTimeMillis();
        ConversationEntity entity = new ConversationEntity();
        entity.setConId(ConversationEntity.genConId(receiverId, senderId));
        entity.setUserIdSendReferral(senderId);
        entity.setUserIdReceiveReferral(receiverId);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        conversationDao.addConversation(entity);
    }

    public List<ConversationResponseDto> getConversationRecent(Long myId) {
        List<ConversationEntity> entities = conversationDao.findConversationRecent(myId);
        List<MessageEntity> lastMessList = new ArrayList<>();
        List<UserInfoEntity> friendList = new ArrayList<>();
        for (ConversationEntity entity : entities) {
            MessageEntity lastMess = messageDao.getLastMessOf(entity.getConId());
            long friendId = myId == entity.getUserIdSendReferral() ? entity.getUserIdReceiveReferral() : entity.getUserIdSendReferral();
            UserInfoEntity userInfo = userInfoDao.findById(friendId);
            lastMessList.add(lastMess);
            friendList.add(userInfo);
        }
        List<ConversationResponseDto> dtos = conversationMapper.toConversationResponseDtos(entities, lastMessList, friendList);
        return dtos;
    }
}
