package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.service.EmailService;
import com.songheqing.microforum.service.VerificationCodeService;
import com.songheqing.microforum.utils.VerificationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    // 注入邮件服务
    @Autowired
    private EmailService emailService;

    // 注入 Redis 模板
    @Autowired
    private StringRedisTemplate redisTemplate; // RedisTemplate 默认使用 JDK 序列化，如果需要可配置 JSON 序列化器

    // 验证码过期时间（分钟）
    private static final long CODE_EXPIRATION_MINUTES = 5;

    // Redis key 的前缀，用于区分不同类型的验证码
    private static final String EMAIL_VERIFICATION_PREFIX = "email_verify_code:";

    /**
     * 发送邮箱验证码
     * 
     * @param email 接收验证码的邮箱地址
     * @param type  验证码类型
     * @return 生成的验证码
     */
    @Override
    public String sendVerificationCode(String email, String type) {
        // 1. 生成验证码
        String code = VerificationCodeGenerator.generateCode();

        // 2. 将验证码存入 Redis，并设置过期时间
        String redisKey = EMAIL_VERIFICATION_PREFIX + type + ":" + email;
        redisTemplate.opsForValue().set(redisKey, code, CODE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        // 3. 准备邮件内容
        String subject = "";
        String text = "";
        if (type.equals("register")) {
            subject = "您的注册验证码是 " + code;
            text = "您的注册验证码是 " + code + "，该验证码" + CODE_EXPIRATION_MINUTES + "分钟内有效。请勿泄露。";
        }
        if (type.equals("login")) {
            subject = "您的登录验证码是 " + code;
            text = "您的登录验证码是 " + code + "，该验证码" + CODE_EXPIRATION_MINUTES + "分钟内有效。请勿泄露。";
        }

        // 4. 发送邮件
        emailService.sendSimpleEmail(email, subject, text);

        return code;
    }

    /**
     * 校验邮箱验证码
     * 
     * @param email 邮箱地址
     * @param code  用户输入的验证码
     * @return 校验结果
     */
    @Override
    public boolean verifyCode(String email, String code, String type) {
        String redisKey = EMAIL_VERIFICATION_PREFIX + type + ":" + email;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode == null) {
            // 验证码不存在或已过期
            return false;
        }

        if (storedCode.equals(code)) {
            // 验证成功后，立即删除 Redis 中的验证码，防止重复使用
            redisTemplate.delete(redisKey);
            return true;
        } else {
            // 验证码不匹配
            return false;
        }
    }
}