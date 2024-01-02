package com.example.chat_app.daos.impls;

import com.example.chat_app.daos.impls.repository.ConversationRepository;
import com.example.chat_app.daos.interfaces.ConversationDao;
import com.example.chat_app.models.ConversationEntity;
import com.example.chat_app.models.ReferralEntity;
import com.example.chat_app.models.postgresql.ConversationPostgreEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component("ConversationDao")
@Slf4j
public class ConversationImpl implements ConversationDao {
    @Autowired
    ConversationRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public void addConversation(ConversationEntity entity) {
        ConversationPostgreEntity postgreEntity = this.mapper.map(entity, ConversationPostgreEntity.class);
        entityManager.persist(postgreEntity);
    }

    @Override
    public List<ConversationEntity> findConversationRecent(Long myId) {
        TypedQuery<ConversationPostgreEntity> query = entityManager.createQuery(
                "select c from ConversationPostgreEntity  c " +
                        "where c.userIdSendReferral = :myId or c.userIdReceiveReferral= :myId " +
                        "order by updatedAt desc ",
                    ConversationPostgreEntity.class
                );
        query.setParameter("myId", myId);
        List<ConversationPostgreEntity> postgreEntities =
                query.getResultList();

        List<ConversationEntity> entities = new ArrayList<>();
        for (ConversationPostgreEntity c : postgreEntities) {
            ConversationEntity entity = mapper.map(c, ConversationEntity.class);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public long totalConversation(Long userId) {
        return repository.countBy(userId);
    }

    @Override
    public int updateConversation(String conId, Long now) {
        return repository.update(conId, now);
    }
}
