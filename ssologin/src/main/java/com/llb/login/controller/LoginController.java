package com.llb.login.controller;

import com.llb.login.pojo.User;
import com.llb.login.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @Author llb
 * Date on 2019/11/29
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    //模拟数据库中的用户
    private static Set<User> dbUser;
    static{
        dbUser = new HashSet<>();
        dbUser.add(new User("张三", "123123"));
        dbUser.add(new User("李四", "12341234"));
        dbUser.add(new User("王五", "1234512345"));
    }

    @RequestMapping("")
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        //获取相应地址
        String target = (String) request.getSession().getAttribute("target");
        //与数据库用户数据进行对比
        Optional<User> first = dbUser.stream().filter(dbUser -> dbUser.getUsername().equals(username) &&
                dbUser.getPassword().equals(password)).findFirst();
        //判断用户登录信息是否正确
        if(first.isPresent()) {
            //保存用户登录信息
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("TOKEN", token);
            cookie.setDomain("sso.com");
            response.addCookie(cookie);
            LoginCacheUtil.loginUser.put(token, first.get());
        } else {
            //登录失败
            request.setAttribute("msg", "用户名或密码错误，请重新输入");
            return "login";
        }
        ModelAndView modelAndView = new ModelAndView("redirect:"+target);
        //重定向到target
        return "redirect:" + target;
    }

    /**
     * 通过登录，获取token信息，并将本次登录的cookie信息保存下来
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<User> info(String token) {
        if(!StringUtils.isEmpty(token)) {
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>((User) null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 退出登录
     * @param cookie
     * @return
     */
    @RequestMapping("/loginOut")
    public String loginOut(@CookieValue(name="TOKEN", required = false) Cookie cookie,
                           @RequestParam(defaultValue = "", required = false) String target) {
        String value = cookie.getValue();
        //删除保存的用户信息
        LoginCacheUtil.loginUser.remove(value);
        //将cookie设置为过期
        cookie.setMaxAge(0);
        return "login";
    }
}
