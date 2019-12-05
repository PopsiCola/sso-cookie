package com.llb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author llb
 * Date on 2019/11/29
 */
@SpringBootApplication
public class CartApp {
    public static void main(String[] args) {
        SpringApplication.run(CartApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
