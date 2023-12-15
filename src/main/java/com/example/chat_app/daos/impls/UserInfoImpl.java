package com.example.chat_app.daos.impls;

import com.example.chat_app.daos.impls.repository.UserInfoRepository;
import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.models.UserInfoEntity;
import com.example.chat_app.models.postgresql.UserInfoPostgreEntity;
import com.example.chat_app.utils.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserInfoDao")
@Slf4j
public class UserInfoImpl implements UserInfoDao {
    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public int changeStatus(UserStatus status, Long userId) {
       return repository.updateStatus(status.toString(), userId);
    }

    @Override
    public void insertEntity(UserInfoEntity entity) {
        log.info("Saving userentity");
        UserInfoPostgreEntity postgreEntity = mapper.map(entity, UserInfoPostgreEntity.class);
        repository.insertEntity(postgreEntity);
    }
}
