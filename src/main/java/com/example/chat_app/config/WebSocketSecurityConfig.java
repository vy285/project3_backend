package com.example.chat_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/ws/**").permitAll()
                .simpDestMatchers("/app/**/**").permitAll()
                .simpDestMatchers("/app/chat").permitAll()
                .simpDestMatchers("/chat/**", "/chat").permitAll()
                .anyMessage().permitAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true; // Tắt same-origin policy để không bị ảnh hưởng đến WebSocket
    }
}
