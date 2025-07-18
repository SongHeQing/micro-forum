package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.User;
import com.songheqing.microforum.mapper.UserMapper;
import com.songheqing.microforum.service.UserService;
import com.songheqing.microforum.service.VerificationCodeService;
import com.songheqing.microforum.utils.JwtUtil;
import com.songheqing.microforum.vo.LoginInfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     * 
     * @param user 用户信息
     * @return 登录信息
     */
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

    /**
     * 用户注册，发送验证码
     * 
     * @param user 用户信息
     */
    @Override
    public void register(User user) {
        // 校验是否获取过验证码
        String redisKey = "email_verify_code:register:" + user.getEmail();
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisValue != null) {
            throw new RuntimeException("请勿重复获取验证码");
        }

        // 1. 检查用户是否存在
        Integer userCheck = userMapper.findByUsername(user.getUsername());
        if (userCheck != null) {
            throw new RuntimeException("用户已存在");
        }

        // 2. 检查邮箱是否存在
        Integer userCheckEmail = userMapper.findByEmail(user.getEmail());
        if (userCheckEmail != null) {
            throw new RuntimeException("邮箱已存在");
        }

        // 4. 发送验证码
        verificationCodeService.sendVerificationCode(user.getEmail(), "register");
    }

    /**
     * 校验注册验证码，如果验证通过则创建用户
     * 
     * @param email 邮箱
     * @param code  验证码
     * @param type  类型
     */
    @Override
    public void verifyRegisterCode(User user, String email, String code) {
        // 1. 校验验证码
        boolean isVerify = verificationCodeService.verifyCode(email, code, "register");
        if (!isVerify) {
            throw new RuntimeException("验证码错误或已过期");
        }
        // 2. 创建用户
        userMapper.insert(user);
    }
}
