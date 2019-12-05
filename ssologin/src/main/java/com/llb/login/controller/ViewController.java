package com.llb.login.controller;

import com.llb.login.pojo.User;
import com.llb.login.utils.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * @Author llb
 * Date on 2019/11/29
 */
@Controller
@RequestMapping("/showLogin")
public class ViewController {

    @GetMapping("")
    public String toLogin(@RequestParam(required = false, defaultValue = "") String target,
                          HttpSession session, @CookieValue(name = "TOKEN", required = false) Cookie cookie) {
        if(StringUtils.isEmpty(target)) {
            target = "http://www.sso.com:9001/main";
        }
        //如果已经登录的用户，再次访问登录系统时，就要重定向到首页不允许再次登录
        //获取token的值
        if(cookie != null) {
            String value = cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(value);
            if(user != null) {
                return "redirect:"+ target;
            }
        }
        session.setAttribute("target", target);
        return "login";
    }

}
