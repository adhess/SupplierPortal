package com.adhess.org.notification.service.notification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate template;


//    public WebSocketController(SimpMessagingTemplate template) {
//        this.template = template;
//    }

    @MessageMapping("/send/message")
    public void onReceiveMessage(String channel, String message) {
//        String[] users = WebSocketConfiguration.users;
//        System.out.println("users length: " + users.length);
//        for (int i = 0; i < users.length; i++) {
//            this.template.convertAndSend(users[i], message);
//        }
        this.template.convertAndSend(channel, message);
        System.out.println(channel + " : " + message);
    }


}
