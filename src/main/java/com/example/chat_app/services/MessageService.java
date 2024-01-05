package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.ConversationDao;
import com.example.chat_app.daos.interfaces.MessageDao;
import com.example.chat_app.dtos.response.MessageResponseDto;
import com.example.chat_app.models.ConversationEntity;
import com.example.chat_app.models.MessageEntity;
import com.example.chat_app.utils.MessageStatus;
import com.example.chat_app.utils.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;

    @Autowired
    ConversationDao conversationDao;

    @Transactional
    public MessageEntity createMessage(long senderId, long receiverId, String content, String type) {
        MessageEntity entity = new MessageEntity();
        Long now = System.currentTimeMillis();
        entity.setConId(ConversationEntity.genConId(senderId, receiverId));
        entity.setSenderId(senderId);
        entity.setReceiverId(receiverId);
        entity.setContent(content);
        entity.setType(MessageType.valueOf(type));
        entity.setIsRead(false);
        entity.setStatus(MessageStatus.NORMAL);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity = messageDao.addMessage(entity);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tạo tin nhắn thất bại");
        }
        conversationDao.updateConversation(ConversationEntity.genConId(senderId, receiverId), now);
        return entity;
    }

    public void markRead(long receiverId, String conId, long messageId) {
        messageDao.updateReadBefore(receiverId, conId, messageId);
    }

    public List<MessageResponseDto> getHistoryMessage(String conId, Long length) {
        List<MessageEntity> entities = messageDao.findMessage(conId, length);
        List<MessageResponseDto> dtos = new ArrayList<>();
        for (MessageEntity entity : entities) {
            MessageResponseDto dto = new MessageResponseDto(
                    entity.getMessId(),
                    entity.getConId(),
                    entity.getSenderId(),
                    entity.getReceiverId(),
                    entity.getIsRead(),
                    entity.getContent(),
                    entity.getStatus().toString(),
                    entity.getType().toString(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
