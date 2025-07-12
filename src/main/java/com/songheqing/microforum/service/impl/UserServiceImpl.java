package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.User;
import com.songheqing.microforum.mapper.UserMapper;
import com.songheqing.microforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        User login = userMapper.login(user);
        return login;
    }
}
