package com.songheqing.microforum.controller;

import com.songheqing.microforum.entity.User;
import com.songheqing.microforum.service.UserService;
import com.songheqing.microforum.vo.Result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Result<Object> login(User user) {
        User login = userService.login(user);
        if (login == null)
            return Result.error("用户名或密码错误");
        return Result.success();
    }
}
