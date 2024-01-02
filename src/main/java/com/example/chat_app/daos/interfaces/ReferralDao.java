package com.example.chat_app.daos.interfaces;

import com.example.chat_app.models.ReferralEntity;

import java.util.Optional;

public interface ReferralDao {
    public void insertReferral(ReferralEntity entity);

    public int updateStatus(Long senderId, Long receiverId);

    public ReferralEntity statusOfReferral(Long myId, Long otherId);

    public void deleteReferral(Long referralId);
}
