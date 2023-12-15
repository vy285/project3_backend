package com.example.chat_app;

import com.example.chat_app.daos.impls.repository.AccountRepository;
import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.models.postgresql.AccountPostgreEntity;
import com.example.chat_app.utils.UserStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ChatAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = (ApplicationContext) SpringApplication.run(ChatAppApplication.class, args);
		UserInfoDao userInfoDao = context.getBean(UserInfoDao.class);
//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//		// Thời gian đầu vào cho method A
//		LocalDateTime inputTime = LocalDateTime.now();
//
//		// Lên lịch thực hiện method A vào thời điểm time + 5 phút
//		scheduler.schedule(() -> methodA(inputTime), 1, TimeUnit.MINUTES);
//
//		// Đóng ScheduledExecutorService sau một khoảng thời gian
//		scheduler.schedule(() -> scheduler.shutdown(), 2, TimeUnit.MINUTES);

		AccountPostgreEntity entity = new AccountPostgreEntity();
//		entity.setUserId(9L);
//		entity.setPassword("1234");
		entity.setGmail("aasd");
		AccountRepository repository = context.getBean(AccountRepository.class);
	}
	// Phương thức A
	private static void methodA(LocalDateTime inputTime) {
		System.out.println("Method A is executed at: " + LocalDateTime.now());

		// Thực hiện phương thức B hoặc thêm mã logic bạn muốn ở đây
		methodB();
	}

	// Phương thức B
	private static void methodB() {
		System.out.println("Method B is executed at: " + LocalDateTime.now());
	}
}
