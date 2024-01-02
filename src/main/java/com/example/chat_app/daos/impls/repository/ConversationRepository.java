package com.example.chat_app.daos.impls.repository;

import com.example.chat_app.models.postgresql.ConversationPostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationPostgreEntity, String> {
    @Query(value = "select count(c) from ConversationPostgreEntity c where c.userIdSendReferral = :userId or c.userIdReceiveReferral = :userId")
    public long countBy(Long userId);

    @Modifying
    @Query(value = "update conversation set updated_at = :now where con_id = :conId", nativeQuery = true)
    public int update(String conId, Long now);
}
