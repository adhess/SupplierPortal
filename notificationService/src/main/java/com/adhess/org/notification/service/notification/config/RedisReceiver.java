package com.adhess.org.notification.service.notification.config;

import com.adhess.org.notification.service.notification.Controller.WebSocketController;
import com.adhess.org.notification.service.notification.model.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@RestController
public class RedisReceiver implements MessageListener {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private WebSocketController webSocketController;

    @Autowired
    private ReactiveRedisTemplate<String, Notification> redisTemplateEmployee;

    private ReactiveListOperations<String, Notification> reactiveListOpsEmployee;

    @PostConstruct
    public void setup() {
        reactiveListOpsEmployee = redisTemplateEmployee.opsForList();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSender.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Notification notification = objectMapper.readValue(new String(message.getBody()), Notification.class);
            reactiveListOpsEmployee.leftPush(notification.getUsername(), notification).subscribe();
            LOGGER.info("Topic: " + notification.getValue());
            webSocketController.onReceiveMessage("/" + notification.getUsername(), notification.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("size")
    public List<Notification> givenEmployeeId_whenGet_thenReturnsEmployee() {
        Flux<Notification> fetchedEmployee = reactiveListOpsEmployee.range("notifications",0L,-1L);
        List<Notification> list1 = fetchedEmployee.collectList().block();
        System.out.println(list1.size());
        return list1;
    }

}
