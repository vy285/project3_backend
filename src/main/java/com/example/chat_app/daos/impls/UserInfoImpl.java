package com.example.chat_app.daos.impls;

import com.example.chat_app.daos.impls.repository.UserInfoRepository;
import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.models.postgresql.UserInfoPostgreEntity;
import com.example.chat_app.utils.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("UserInfoDao")
@Slf4j
public class UserInfoImpl implements UserInfoDao {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public int changeStatus(UserStatus status, Long userId) {
        System.out.println("status: " + status.toString());
        return repository.updateStatus(status.toString(), userId);
    }

    @Override
    public void insertEntity(UserInfoEntity entity) {
        log.info("Saving userentity");
        UserInfoPostgreEntity postgreEntity = mapper.map(entity, UserInfoPostgreEntity.class);
        repository.insertEntity(postgreEntity);
    }

    @Override
    public List<UserInfoEntity> findByNickname(String nickname, Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        TypedQuery<UserInfoPostgreEntity> query = entityManager.createQuery(
                "SELECT u FROM UserInfoPostgreEntity u WHERE u.nickname = :nickname AND u.status = 'online'" +
                        "order by u.updatedAt DESC",
                UserInfoPostgreEntity.class
        );
        query.setParameter("nickname", nickname);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<UserInfoPostgreEntity> result = query.getResultList();
        List<UserInfoEntity> entities = new ArrayList<>();
        for (UserInfoPostgreEntity u : result) {
            UserInfoEntity entity = this.mapper.map(u, UserInfoEntity.class);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public long countByNickname(String nickname) {
        return repository.countBy(nickname);
    }

    @Override
    public List<UserInfoEntity> findPersonAccept(long userId, String nicknameFriend) {
        TypedQuery<UserInfoPostgreEntity> query = entityManager.createQuery(
                "select u from UserInfoPostgreEntity u " +
                        "where (u.userId in (select r.receiverId from ReferralPostgreEntity r where r.senderId = :userId and r.status = 'yes') " +
                                "or " +
                                "u.userId in  (select r.senderId from ReferralPostgreEntity r where r.receiverId = :userId and r.status = 'yes') " +
                                ") " +
                        "and (u.nickname = :nicknameFriend)",
                UserInfoPostgreEntity.class
        );
        query.setParameter("nicknameFriend", nicknameFriend);
        query.setParameter("userId", userId);
        List<UserInfoPostgreEntity> postgreEntities = query.getResultList();
        List<UserInfoEntity> entities = new ArrayList<>();
        for (UserInfoPostgreEntity u : postgreEntities) {
            UserInfoEntity entity = mapper.map(u, UserInfoEntity.class);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public List<UserInfoEntity> findPersonWait(long userId, String nicknameFriend) {
        TypedQuery<UserInfoPostgreEntity> query = entityManager.createQuery(
                "select u from UserInfoPostgreEntity u " +
                        "where (u.userId in (select r.receiverId from ReferralPostgreEntity r where r.senderId = :userId and r.status = 'wait') " +
                                "or " +
                                "u.userId in  (select r.senderId from ReferralPostgreEntity r where r.receiverId = :userId and r.status = 'wait') " +
                                ") " +
                        "and (u.nickname = :nicknameFriend)",
                UserInfoPostgreEntity.class
        );
        query.setParameter("nicknameFriend", nicknameFriend);
        query.setParameter("userId", userId);
        List<UserInfoPostgreEntity> postgreEntities = query.getResultList();
        List<UserInfoEntity> entities = new ArrayList<>();
        for (UserInfoPostgreEntity u : postgreEntities) {
            UserInfoEntity entity = mapper.map(u, UserInfoEntity.class);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public List<UserInfoEntity> findPersonNoSend(long userId, String nicknameFriend) {
        TypedQuery<UserInfoPostgreEntity> query = entityManager.createQuery(
                "select u from UserInfoPostgreEntity u " +
                        "where (u.userId not in (select r.receiverId from ReferralPostgreEntity r where r.senderId = :userId) " +
                                "and " +
                                "u.userId not in  (select r.senderId from ReferralPostgreEntity r where r.receiverId = :userId) " +
                                ") " +
                        "and (u.nickname = :nicknameFriend) ",
                UserInfoPostgreEntity.class
        );
        query.setParameter("nicknameFriend", nicknameFriend);
        query.setParameter("userId", userId);
        List<UserInfoPostgreEntity> postgreEntities = query.getResultList();
        List<UserInfoEntity> entities = new ArrayList<>();
        for (UserInfoPostgreEntity u : postgreEntities) {
            UserInfoEntity entity = mapper.map(u, UserInfoEntity.class);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public String findAvatarOf(Long userId) {
        Optional<UserInfoPostgreEntity> postgreEntityOptional = repository.findById(userId);
        if (postgreEntityOptional.isEmpty()) return null;
        return postgreEntityOptional.get().getAvatar();
    }
}
