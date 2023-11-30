package com.example.chat_app.daos.impls.repository;

import com.example.chat_app.models.postgresql.AccountPostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountPostgreEntity, Long> {
    @Query(value = "select * from account where gmail = ?1", nativeQuery = true)
    Optional<AccountPostgreEntity> findByGmail(String gmail);

    @Query(value = "select * from account where user_id = ?1", nativeQuery = true)
    Optional<AccountPostgreEntity> findByUserId(Long userId);
}
