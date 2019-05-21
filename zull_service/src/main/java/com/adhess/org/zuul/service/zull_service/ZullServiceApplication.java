package com.adhess.org.zuul.service.zull_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.text.DecimalFormat;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZullServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZullServiceApplication.class, args);
        double ans = 45.21452;
        System.out.println(new DecimalFormat("##.#").format(ans));
    }

}
