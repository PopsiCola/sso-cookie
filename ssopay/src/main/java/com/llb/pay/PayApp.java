package com.llb.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author llb
 * Date on 2019/11/29
 */
@SpringBootApplication
public class PayApp {

    public static void main(String[] args) {
        SpringApplication.run(PayApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
