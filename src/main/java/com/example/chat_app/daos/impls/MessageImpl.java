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
    public String getLastMessOf(String conId, Long myId) {
        Optional<MessagePostgreEntity> postgreEntityOptional = repository.findLastMessBy(conId);
        if (postgreEntityOptional.isEmpty()) return "Các bạn có thể liên lạc với nhau";
        MessagePostgreEntity postgreEntity = postgreEntityOptional.get();
        String personSend = myId == postgreEntity.getSenderId() ? "You: " : "Friend: ";
        System.out.println("kieu content: " + postgreEntity.getType());
        String mess = postgreEntity.getType().equals("TEXT") ? postgreEntity.getContent() : "gửi một ảnh";
        return String.format("%s %s", personSend, mess);
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
