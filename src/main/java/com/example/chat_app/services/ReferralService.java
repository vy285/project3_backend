package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.ConversationDao;
import com.example.chat_app.daos.interfaces.ReferralDao;
import com.example.chat_app.models.ConversationEntity;
import com.example.chat_app.models.ReferralEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class ReferralService {
    @Autowired
    ReferralDao referralDao;

    @Autowired
    ConversationDao conversationDao;

    public void addReferral(Long senderId, Long receiverId) {
        ReferralEntity entity = new ReferralEntity();
        Long now = System.currentTimeMillis();
        entity.setSenderId(senderId);
        entity.setReceiverId(receiverId);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        referralDao.insertReferral(entity);
    }

    @Transactional
    public void acceptReferral(Long senderId, Long receiverId) {
        int countUpdate = referralDao.updateStatus(senderId, receiverId);

        // khi dog y ket ban thi ta them cuoc tro chuyen moi
        Long now = System.currentTimeMillis();
        ConversationEntity entity = new ConversationEntity();
        entity.setConId(ConversationEntity.genConId(receiverId, senderId));
        entity.setUserIdSendReferral(senderId);
        entity.setUserIdReceiveReferral(receiverId);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        conversationDao.addConversation(entity);
        if (countUpdate != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accept failed");
        }
    }

    public void deleteReferral(Long referralId) {
        referralDao.deleteReferral(referralId);
    }

}
