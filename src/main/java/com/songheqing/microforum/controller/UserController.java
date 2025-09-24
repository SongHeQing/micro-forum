package com.songheqing.microforum.controller;

import com.songheqing.microforum.entity.UserEntity;
import com.songheqing.microforum.request.UserRegisterRequest;
import com.songheqing.microforum.service.UserService;

import com.songheqing.microforum.vo.Result;
import com.songheqing.microforum.vo.LoginInfo;
import com.songheqing.microforum.vo.UserHomeVO;
import com.songheqing.microforum.vo.UserProfileVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户", description = "处理用户相关的操作")
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
    @Operation(summary = "用户登录", description = "支持邮箱做为账号和密码登录")
    @PostMapping("/login")
    public Result<LoginInfo> login(@RequestBody UserEntity user) {
        LoginInfo loginInfo = userService.login(user);
        return Result.success(loginInfo);
    }

    /**
     * 用户注册
     * 
     * @param user 用户信息
     * @return 注册信息
     */
    @Operation(summary = "用户注册", description = "仅支持邮箱注册")
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
    @Operation(summary = "校验注册验证码", description = "仅支持邮箱接收验证码")
    @PostMapping("/verifyRegisterCode")
    public Result<Object> verifyRegisterCode(@RequestBody @Valid UserRegisterRequest userRegisterRequest,
            @Validated @NotBlank(message = "验证码不能为空") @Pattern(regexp = "^[0-9]{6}$", message = "验证码长度6位，只能包含数字") // 验证码长度6位，只能包含数字
            String code) {
        userService.verifyRegisterCode(userRegisterRequest, code);
        return Result.success("验证码验证成功");
    }

    /**
     * 获取指定用户的主页信息
     *
     * @param userId 用户ID
     * @return 用户主页信息
     */
    @Operation(summary = "获取指定用户主页信息", description = "获取指定用户主页信息")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{userId}/home")
    public Result<UserHomeVO> getUserHome(@NotNull(message = "用户ID不能为空") @PathVariable Long userId) {
        UserHomeVO userHomeVO = userService.getUserHomeById(userId);
        return Result.success(userHomeVO);
    }

    /**
     * 获取当前登录用户的统计数据
     * 
     * @return 当前用户统计数据
     */
    @Operation(summary = "获取当前登录用户的统计数据", description = "展示当前登录用户的统计数据")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/profile")
    public Result<UserProfileVO> getCurrentUserProfile() {
        UserProfileVO userProfileVO = userService.getUserProfileById();
        return Result.success(userProfileVO);
    }
}
