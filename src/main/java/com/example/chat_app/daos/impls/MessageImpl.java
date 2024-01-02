package com.example.chat_app.daos.impls;

import com.example.chat_app.daos.impls.repository.MessageRepository;
import com.example.chat_app.daos.interfaces.MessageDao;
import com.example.chat_app.models.MessageEntity;
import com.example.chat_app.models.postgresql.MessagePostgreEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("MessageDao")
public class MessageImpl implements MessageDao {
    @Autowired
    MessageRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public MessageEntity getLastMessOf(String conId) {
        Optional<MessagePostgreEntity> postgreEntityOptional = repository.findLastMessBy(conId);
        if (postgreEntityOptional.isEmpty()) return null;
        return mapper.map(postgreEntityOptional.get(), MessageEntity.class);
    }

    @Override
    public MessageEntity addMessage(MessageEntity entity) {
        MessagePostgreEntity postgreEntity = mapper.map(entity, MessagePostgreEntity.class);
        postgreEntity = repository.save(postgreEntity);
        return mapper.map(postgreEntity, MessageEntity.class);
    }

    @Override
    public Long updateReadBefore(Long receiverId, String conId, Long messageId) {
        return repository.updateReadBefore(receiverId, conId, messageId);
    }

    @Override
    public List<MessageEntity> findMessage(String conId, Long length) {
        List<MessagePostgreEntity> postgreEntities = repository.findMessageBy(conId, length);
        List<MessageEntity> entities = new ArrayList<>();
        for (MessagePostgreEntity postgreEntity : postgreEntities) {
            MessageEntity entity = mapper.map(postgreEntity, MessageEntity.class);
            entities.add(entity);
        }
        return entities;
    }
}
