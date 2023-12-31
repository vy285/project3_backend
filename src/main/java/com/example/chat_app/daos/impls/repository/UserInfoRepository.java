package com.example.chat_app.daos.impls.repository;

import com.example.chat_app.models.postgresql.UserInfoPostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoPostgreEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "update UserInfoPostgreEntity u set u.status = ?1 where u.userId = ?2")
    int updateStatus(String status, Long userId);

    @Modifying
    @Query(value = "insert into user_info (user_id, nickname, created_at, updated_at) values (:#{#entity.userId}, :#{#entity.nickname}, :#{#entity.createdAt}, :#{#entity.updatedAt})", nativeQuery = true)
    void insertEntity(UserInfoPostgreEntity entity);

    @Query(value = "select * from user_info where nickname = ?1 and status = 'online'", nativeQuery = true)
    List<UserInfoPostgreEntity> findBy(String nickname);

    @Query(value = "select count(*) from user_info where nickname = ?1 and status = 'online'", nativeQuery = true)
    long countBy(String nickname);
}
