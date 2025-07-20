package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.entity.User;
import com.songheqing.microforum.mapper.UserMapper;
import com.songheqing.microforum.exception.UserException;
import com.songheqing.microforum.request.UserRegisterRequest;
import com.songheqing.microforum.service.UserService;
import com.songheqing.microforum.service.VerificationCodeService;
import com.songheqing.microforum.utils.JwtUtil;
import com.songheqing.microforum.vo.LoginInfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
        dataMap.put("username", userLogin.getNickname());

        String jwt = jwtUtil.generateToken(dataMap);
        LoginInfo loginInfo = new LoginInfo(userLogin.getId(), userLogin.getNickname(), userLogin.getEmail(), jwt);
        return loginInfo;
    }

    /**
     * 用户注册，发送验证码
     * 
     * @param user 用户信息
     */
    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        // 1校验是否获取过验证码
        String redisKey = "email_verify_code:register:" + userRegisterRequest.getEmail();
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisValue != null) {
            throw new UserException("请勿重复获取验证码");
        }

        // 2. 检查邮箱是否存在
        Integer userCheckEmail = userMapper.findByEmail(userRegisterRequest.getEmail());
        if (userCheckEmail != null) {
            throw UserException.emailAlreadyExists();
        }

        // 3. 发送验证码
        try {
            verificationCodeService.sendVerificationCode(userRegisterRequest.getEmail(), "register");
        } catch (Exception e) {
            log.error("验证码发送失败：{}", e.getMessage(), e);
            throw UserException.verificationCodeSendFailed(e);
        }
    }

    /**
     * 校验注册验证码，如果验证通过则创建用户
     * 
     * @param email 邮箱
     * @param code  验证码
     * @param type  类型
     */
    @Override
    public void verifyRegisterCode(UserRegisterRequest userRegisterRequest, String code) {
        // 1. 校验验证码
        boolean isVerify = verificationCodeService.verifyCode(userRegisterRequest.getEmail(), code, "register");
        if (!isVerify) {
            throw UserException.invalidVerificationCode();
        }
        // 2. 创建用户
        User user = new User();
        BeanUtils.copyProperties(userRegisterRequest, user);
        userMapper.insert(user);
    }
}
