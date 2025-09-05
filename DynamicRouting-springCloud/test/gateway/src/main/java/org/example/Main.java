package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.wenzhuo4657.gateway.annotation.EnableGatewayService;


@EnableGatewayService
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.setWebApplicationType(WebApplicationType.REACTIVE);
        springApplication.run(args);
    }
}