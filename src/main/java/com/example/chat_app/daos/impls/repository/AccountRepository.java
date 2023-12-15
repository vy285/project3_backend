package com.example.chat_app.daos.impls.repository;

import com.example.chat_app.models.postgresql.AccountPostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountPostgreEntity, Long> {
    @Query(value = "select * from account where gmail = ?1", nativeQuery = true)
    Optional<AccountPostgreEntity> findByGmail(String gmail);

    @Query(value = "select * from account where user_id = ?1", nativeQuery = true)
    Optional<AccountPostgreEntity> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "update account set password = ?1, updated_at = ?2 where user_id = ?3", nativeQuery = true)
    Integer updatePassword(String password, Long updatedAt, Long userId);

    @Modifying
    @Query(value = "insert into account (gmail, password, created_at, updated_at) values (:#{#entity.gmail}, :#{#entity.password}, :#{#entity.createdAt}, :#{#entity.updatedAt})", nativeQuery = true)
    void insertEntity(AccountPostgreEntity entity);

//    @Modifying
//    @Query(value = "")
}
