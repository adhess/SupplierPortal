package com.adhess.org.notification.service.notification;

import com.adhess.org.notification.service.notification.config.RedisSender;
import com.adhess.org.notification.service.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class NotificationApplication implements CommandLineRunner {

    @Autowired
    private RedisSender redisSender;


    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Notification notification = new Notification("admin", "admin", "my beautiful art is here >>> .... <<<");
        while (true) {
            TimeUnit.SECONDS.sleep(5);
            redisSender.sendDataToRedisQueue(notification);
        }
    }
}
