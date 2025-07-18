package com.songheqing.microforum.controller;

import com.songheqing.microforum.entity.User;
import com.songheqing.microforum.service.UserService;
import com.songheqing.microforum.service.VerificationCodeService;
import com.songheqing.microforum.vo.Result;
import com.songheqing.microforum.vo.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * 
     * @param user 用户信息
     * @return 登录信息
     */
    @GetMapping("/login")
    public Result<Object> login(User user) {
        LoginInfo loginInfo = userService.login(user);
        if (loginInfo == null) {
            log.error("用户名或密码错误");
            return Result.error("用户名或密码错误");
        }
        return Result.success(loginInfo);
    }

    /**
     * 用户注册
     * 
     * @param user 用户信息
     * @return 注册信息
     */
    @PostMapping("/register")
    public Result<Object> register(User user) {
        try {
            userService.register(user);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.success("验证码发送成功");
    }

    /**
     * 校验注册验证码
     * 
     * @param email 邮箱
     * @param code  验证码
     * @return 校验结果
     */
    @PostMapping("/verifyRegisterCode")
    public Result<Object> verifyRegisterCode(User user, String email, String code) {
        try {
            userService.verifyRegisterCode(user, email, code);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.success("验证码验证成功");
    }
}
