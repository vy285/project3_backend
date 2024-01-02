package com.example.chat_app.daos.impls;

import com.example.chat_app.daos.impls.repository.ReferralRepository;
import com.example.chat_app.daos.interfaces.ReferralDao;
import com.example.chat_app.models.ReferralEntity;
import com.example.chat_app.models.postgresql.ReferralPostgreEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component("referralDao")
public class ReferralImpl implements ReferralDao {
    @Autowired
    ReferralRepository repository;

    @Autowired
    ModelMapper mapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void insertReferral(ReferralEntity entity) {
        ReferralPostgreEntity postgreEntity = mapper.map(entity, ReferralPostgreEntity.class);
        repository.save(postgreEntity);
    }

    @Override
    public int updateStatus(Long senderId, Long receiverId) {
        Long now = System.currentTimeMillis();
        return repository.updateStatus(senderId, receiverId, now);
    }

    @Override
    public ReferralEntity statusOfReferral(Long myId, Long otherId) {
        Optional<ReferralPostgreEntity> postgreEntityOptional = repository.findBy(myId, otherId);
        if(postgreEntityOptional.isEmpty()) return null;
        return mapper.map(postgreEntityOptional.get(), ReferralEntity.class);
    }

    @Override
    public void deleteReferral(Long referralId) {
        repository.deleteByReferralId(referralId);
    }
}
