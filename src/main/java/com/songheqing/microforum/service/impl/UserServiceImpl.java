package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.User;
import com.songheqing.microforum.mapper.UserMapper;
import com.songheqing.microforum.service.UserService;
import com.songheqing.microforum.utils.JwtUtil;
import com.songheqing.microforum.vo.LoginInfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginInfo login(User user) {
        User userLogin = userMapper.login(user);
        if (userLogin == null) {
            log.error("用户名或密码错误:{}", user);
            return null;
        }
        // 1. 生成JWT令牌
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", userLogin.getId());
        dataMap.put("username", userLogin.getUsername());

        String jwt = jwtUtil.generateToken(dataMap);
        LoginInfo loginInfo = new LoginInfo(userLogin.getId(), userLogin.getUsername(), userLogin.getEmail(), jwt);
        return loginInfo;
    }
}
