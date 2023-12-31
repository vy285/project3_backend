package com.example.chat_app.daos.interfaces;

import com.example.chat_app.models.MessageEntity;

import java.util.List;

public interface MessageDao {
    public String getLastMessOf(String conId, Long myId);

    public MessageEntity addMessage(MessageEntity entity);

    public Long updateReadBefore(Long receiverId, String conId, Long messageId);

    public List<MessageEntity> findMessage(String conId, Long length);
}
