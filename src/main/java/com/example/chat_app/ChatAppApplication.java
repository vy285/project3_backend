package com.example.chat_app;

import com.example.chat_app.daos.impls.repository.AccountRepository;
import com.example.chat_app.daos.interfaces.UserInfoDao;
import com.example.chat_app.models.postgresql.AccountPostgreEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ChatAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = (ApplicationContext) SpringApplication.run(ChatAppApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowedOrigins("*", "http://localhost:3000", "http://192.168.1.21:3000");
			}
		};
	}
}
