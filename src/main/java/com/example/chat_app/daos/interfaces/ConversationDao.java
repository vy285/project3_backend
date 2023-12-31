package com.example.chat_app.daos.interfaces;

import com.example.chat_app.models.ConversationEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConversationDao {
    public void addConversation(ConversationEntity entity);

    public List<ConversationEntity> findConversationRecent(Long userId);

    public long totalConversation (Long userId);
}
