package com.example.chat_app.daos.impls.repository;

import com.example.chat_app.models.postgresql.MessagePostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessagePostgreEntity, Long> {
    @Query(value = "select * from message where con_id = :conId " +
            "order by created_at DESC " +
            "LIMIT 1", nativeQuery = true)
    public Optional<MessagePostgreEntity> findLastMessBy(String conId);

    @Modifying
    @Transactional
    @Query(value = "update message set is_read = true " +
            "where receiver_id =:receiverId and con_id =:conId and is_read = false and " +
            "created_at <= (select created_at from message where mess_id =:messageId)", nativeQuery = true)
    public long updateReadBefore(long receiverId, String conId, long messageId);

    @Query(value = "select * from message where con_id =:conId " +
            "order by created_at desc limit :length", nativeQuery = true)
    public List<MessagePostgreEntity> findMessageBy(String conId, Long length);
}
