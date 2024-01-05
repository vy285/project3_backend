package com.example.chat_app.services;

import com.example.chat_app.daos.interfaces.AccountDao;
import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.models.AccountEntity;
import com.example.chat_app.models.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AccountService implements UserDetailsService {
    @Autowired
    AccountDao accountDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    EmailService emailService;

    @Override
    public AccountEntity loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<AccountEntity> entityOptional = accountDao.getByUserId(Long.valueOf(userId));
        if (entityOptional.isEmpty()) {
            return null;
        }
        return entityOptional.get();
    }

    public AccountEntity getbyGmail(String gmail) {
        Optional<AccountEntity> entityOptional = accountDao.getByGmail(gmail);
        if (entityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể tìm thấy tài khoản");
        }
        return entityOptional.get();
    }

    public void changePassword(String oldPass, String newPass, Long userId) {
        long now = System.currentTimeMillis();
        int countChange = accountDao.updatePassword(oldPass, newPass, now, userId);
        if (countChange == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thay đổi mật khẩu thất bại");
    }

    @Transactional
    public void verifySuccess(AccountEntity accountEntity, String nickname) {
        try {
            Long now = System.currentTimeMillis();
            accountEntity.setCreatedAt(now);
            accountEntity.setUpdatedAt(now);

            accountEntity = accountDao.saveEntity(accountEntity);

            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUserId(accountEntity.getUserId());
            userInfoEntity.setNickname(nickname);
            userInfoEntity.setCreatedAt(now);
            userInfoEntity.setUpdatedAt(now);
            userInfoDao.insertEntity(userInfoEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gmail đã tồn tại");
        }
    }

    public  String generateRandomNumberString(int length) {
        // Dãy ký tự số từ '0' đến '9'
        String digits = "0123456789";

        // Sử dụng StringBuilder để xây dựng chuỗi kết quả
        StringBuilder stringBuilder = new StringBuilder(length);

        // Tạo đối tượng Random
        Random random = new Random();

        // Tạo chuỗi ngẫu nhiên bằng cách chọn ngẫu nhiên các ký tự từ dãy số
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(digits.length());
            char randomDigit = digits.charAt(randomIndex);
            stringBuilder.append(randomDigit);
        }

        return stringBuilder.toString();
    }

    public void saveVerifyCode(String gmail) {
        log.info("gmail: " + gmail);
        if (checkGmail(gmail) == false) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gmail không đúng định dạng");
        String code = generateRandomNumberString(6);
        Long expired = System.currentTimeMillis() + 5 * 60 * 10000;
        String verifyCode = String.format("%s_%s", code, expired);
        Optional<AccountEntity> entityOptional = accountDao.getByGmail(gmail);
        AccountEntity newEntity = new AccountEntity();
        if (entityOptional.isEmpty()) {
            newEntity.setGmail(gmail);
        } else {
            if (entityOptional.get().isEnabled() == true) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gmail đã tồn tại");
            newEntity = entityOptional.get();
        }
        emailService.sendEmail(gmail, "VerifyCode", code);
        newEntity.setVerifyCode(verifyCode);
        accountDao.saveEntity(newEntity);
    }

    public static boolean checkGmail(String text) {
        // Định dạng chuỗi cho địa chỉ email Gmail
        String gmailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Tạo Pattern object
        Pattern pattern = Pattern.compile(gmailRegex);

        // Tạo Matcher object
        Matcher matcher = pattern.matcher(text);

        // Kiểm tra xem chuỗi khớp với định dạng không
        return matcher.matches();
    }
}
