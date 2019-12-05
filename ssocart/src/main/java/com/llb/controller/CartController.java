package com.llb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author llb
 * Date on 2019/11/29
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private RestTemplate restTemplate;

    //可以配置到文件中，我这里直接写在这里
    private final String USER_INFO_ADDRESS = "http://www.login.sso.com:9000/login/info?token=";

    @GetMapping("")
    public String showIndex(@CookieValue(name="TOKEN", required = false) Cookie cookie,
                            HttpServletRequest request) {
        if(cookie != null) {
            String token = cookie.getValue();
            if(!StringUtils.isEmpty(token)) {
                Map result = restTemplate.getForObject(USER_INFO_ADDRESS + token, Map.class);
                request.getSession().setAttribute("loginUser", result);
            }
        }
        return "index";
    }
}
