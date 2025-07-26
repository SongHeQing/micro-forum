package com.songheqing.microforum.controller;

import com.songheqing.microforum.entity.UserEntity;
import com.songheqing.microforum.request.UserRegisterRequest;
import com.songheqing.microforum.service.UserService;
import com.songheqing.microforum.vo.Result;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.Valid;

import com.songheqing.microforum.vo.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
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
    public Result<LoginInfo> login(UserEntity user) {
        LoginInfo loginInfo = userService.login(user);
        return Result.success(loginInfo);
    }

    /**
     * 用户注册
     * 
     * @param user 用户信息
     * @return 注册信息
     */
    @PostMapping("/register")
    public Result<Object> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        log.info("收到注册请求: email={}, password={}", userRegisterRequest.getEmail(), userRegisterRequest.getPassword());
        userService.register(userRegisterRequest);
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
    public Result<Object> verifyRegisterCode(@RequestBody @Valid UserRegisterRequest userRegisterRequest,
            @Validated @NotBlank(message = "验证码不能为空") @Pattern(regexp = "^[0-9]{6}$", message = "验证码长度6位，只能包含数字") // 验证码长度6位，只能包含数字
            String code) {
        userService.verifyRegisterCode(userRegisterRequest, code);
        return Result.success("验证码验证成功");
    }
}
