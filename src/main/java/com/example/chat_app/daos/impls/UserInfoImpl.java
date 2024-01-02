package com.example.chat_app.daos.impls;

import com.example.chat_app.daos.impls.repository.UserInfoRepository;
import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.dtos.response.UserInfoResponseDto;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.models.postgresql.ReferralPostgreEntity;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    //tim nhung nguoi co ten nickname va khac id

    @Override
    public List<UserInfoEntity> findBy(String nickname, long myId) {
        List<UserInfoPostgreEntity> postgreEntities = repository.findByNickname(nickname, myId);
        List<UserInfoEntity> entities = new ArrayList<>();
        for(UserInfoPostgreEntity postgreEntity : postgreEntities) {
            UserInfoEntity entity = mapper.map(postgreEntity, UserInfoEntity.class);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    public List<UserInfoResponseDto> findWaitReferral(long myId) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "select u, r " +
                        "from UserInfoPostgreEntity u join ReferralPostgreEntity r " +
                        "on r.senderId = u.userId " +
                        "where r.receiverId =:myId and r.status = 'WAIT'"
                , Object[].class);
        query.setParameter("myId", myId);
        List<Object[]> result = query.getResultList();

        List<UserInfoResponseDto> dtos = new ArrayList<>();

        for (Object[] item : result) {
            UserInfoPostgreEntity userInfoPostgre = (UserInfoPostgreEntity) item[0];
            ReferralPostgreEntity referralPostgre = (ReferralPostgreEntity) item[1];
            UserInfoResponseDto dto = new UserInfoResponseDto();
            dto.setUserId(userInfoPostgre.getUserId());
            dto.setStatus(userInfoPostgre.getStatus().toString());
            dto.setAddress(userInfoPostgre.getAddress());
            dto.setAvatar(userInfoPostgre.getAvatar());
            dto.setNickname(userInfoPostgre.getNickname());
            dto.setDateOfBirth(dto.getDateOfBirth());
            dto.setCreatedAt(userInfoPostgre.getCreatedAt());
            dto.setUpdatedAt(userInfoPostgre.getUpdatedAt());
            dto.setStatusReferral("NORELY");
            dto.setReferralId(referralPostgre.getReferralId());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public UserInfoEntity findById(long myId) {
        Optional<UserInfoPostgreEntity> postgreEntityOptional = repository.findById(myId);
        if (postgreEntityOptional.isEmpty()) return null;
        return mapper.map(postgreEntityOptional.get(), UserInfoEntity.class);
    }

    @Override
    @Transactional
    public Integer updateUser(UserInfoEntity entity) {
        UserInfoPostgreEntity postgreEntity = mapper.map(entity, UserInfoPostgreEntity.class);
        return repository.updateUser(postgreEntity);
    }
}
