package com.example.chat_app.daos.interfaces;

import com.example.chat_app.models.ReferralEntity;

public interface ReferralDao {
    public void insertReferral(ReferralEntity entity);

    public int updateStatus(Long senderId, Long receiverId);

    public void deleteReferral(Long senderId, Long receiverId);
}
