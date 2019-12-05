package com.llb.login.utils;

import com.llb.login.pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录缓存工具，应该放到redis 数据库中
 * @Author llb
 * Date on 2019/11/29
 */

public class LoginCacheUtil {

    public static Map<String, User> loginUser = new HashMap<>();
}
