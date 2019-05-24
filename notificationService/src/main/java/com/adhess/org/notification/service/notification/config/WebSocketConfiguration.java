package com.adhess.org.notification.service.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/socket")
//                .setAllowedOrigins("*")
//                .withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        super.configureMessageBroker(registry);
//        registry.setApplicationDestinationPrefixes("/app")
//                .enableSimpleBroker("/admin","/chat");
//    }

    public static String[] users;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        super.configureMessageBroker(registry);
        users = (String[]) getAllUsername().toArray(new String[0]);
        String ans = "";
        for (int i = 0; i < users.length; i++) {
            users[i] = "/" + users[i];
            ans += users[i];
        }
        System.out.println("waw ? : " + ans);
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker(users);
    }

    public static List getAllUsername() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:7999/getAllUsername";
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        return responseEntity.getBody();
    }
}
