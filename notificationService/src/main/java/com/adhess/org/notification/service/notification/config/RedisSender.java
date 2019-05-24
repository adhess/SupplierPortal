package com.adhess.org.notification.service.notification.config;

import com.adhess.org.notification.service.notification.model.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSender.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public void sendDataToRedisQueue(Notification input) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(input);
        redisTemplate.convertAndSend(topic.getTopic(), message);
        LOGGER.info("Data - " + message + "sent through Redis Topic - " + topic.getTopic());
    }

}
