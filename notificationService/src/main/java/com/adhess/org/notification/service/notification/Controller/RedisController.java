package com.adhess.org.notification.service.notification.Controller;

import com.adhess.org.notification.service.notification.config.RedisSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class RedisController {
    @Autowired
    private RedisSender redisSender;

//    @GetMapping("get")
//    public void get(){
//        redisSender.sendDataToRedisQueue("message");
//    }
}
