package com.example.chat_app.daos.impls.repository;

import com.example.chat_app.models.postgresql.ReferralPostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ReferralRepository extends JpaRepository<ReferralPostgreEntity, Long> {
//    @Modifying
//    @Query(value = "insert into referral (sender_id, receiver_id, created_at, updated_at) ", nativeQuery = true)
//    public void insertReferral(ReferralPostgreEntity entity);

    @Modifying
    @Query(value = "update referral set status = 'YES', updated_at = :now where sender_id = :senderId and receiver_id = :receiverId", nativeQuery = true)
    public int updateStatus(Long senderId, Long receiverId, Long now);

    @Query(value = "select * from referral " +
            "where (sender_id =:myId and receiver_id =:otherId) " +
            "or (sender_id =:otherId and receiver_id =:myId)", nativeQuery = true)
    public Optional<ReferralPostgreEntity> findBy(long myId, long otherId);

    @Transactional
    @Modifying
    @Query(value = "delete from referral where referral_id = :referralId", nativeQuery = true)
    public void deleteByReferralId(long referralId);
}
