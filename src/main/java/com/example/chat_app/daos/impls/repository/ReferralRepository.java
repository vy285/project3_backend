package com.example.chat_app.daos.impls.repository;

import com.example.chat_app.models.postgresql.ReferralPostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRepository extends JpaRepository<ReferralPostgreEntity, Long> {
//    @Modifying
//    @Query(value = "insert into referral (sender_id, receiver_id, created_at, updated_at) ", nativeQuery = true)
//    public void insertReferral(ReferralPostgreEntity entity);

    @Modifying
    @Query(value = "update referral set status = 'yes' and updated_at = :now where sender_id = :senderId and receiver_id = :receiverId", nativeQuery = true)
    public int updateStatus(Long senderId, Long receiverId, Long now);

    @Modifying
    @Query(value = "delete from referral where sender_id = :senderId and receiver_id = :receiverId", nativeQuery = true)
    public void deleteBy(Long senderId, Long receiverId);
}
