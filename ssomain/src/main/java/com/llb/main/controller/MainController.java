package com.llb.main.controller;

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
@RequestMapping("/main")
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    private final String LOGI_INFO_INFO = "http://www.login.sso.com:9000/login/info?token=";

    @GetMapping("")
    private String index(@CookieValue(name = "TOKEN", required = false) Cookie cookie,
                         HttpServletRequest request) {
        //判断是否登录，如果登录后，根据token获取用户信息
        if(cookie != null) {
            String token = cookie.getValue();
            if(!StringUtils.isEmpty(token)) {
                Map result = restTemplate.getForObject(LOGI_INFO_INFO + token, Map.class);
                request.getSession().setAttribute("loginUser", result);
            }
        }
        return "index";
    }
}
