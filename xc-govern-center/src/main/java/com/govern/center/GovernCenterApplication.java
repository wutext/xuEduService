package com.govern.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GovernCenterApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GovernCenterApplication.class, args);
    }
}
