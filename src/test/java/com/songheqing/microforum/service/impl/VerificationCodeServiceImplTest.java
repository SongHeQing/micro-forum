package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.service.VerificationCodeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.songheqing.microforum.service.EmailService;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VerificationCodeServiceImplTest {

    @Autowired
    private VerificationCodeService verificationCodeService;

    // mock掉邮件发送，避免测试时真的发邮件
    @MockBean
    private EmailService emailService;

    @Test
    public void testSendAndVerifyCode_register() {
        String email = "2063298305@qq.com";
        String type = "register";

        // 发送验证码
        String code = verificationCodeService.sendVerificationCode(email, type);
        Assertions.assertNotNull(code);
        // 校验验证码（应为true）
        boolean result = verificationCodeService.verifyCode(email, code, type);
        Assertions.assertTrue(result);
        // 再次校验（应为false，已删除）
        boolean result2 = verificationCodeService.verifyCode(email, code, type);
        Assertions.assertFalse(result2);
        // 验证邮件服务被调用
        verify(emailService, atLeastOnce()).sendSimpleEmail(eq(email),
                contains("注册验证码"), anyString());
    }

    @Test
    public void testSendAndVerifyCode_login() {
        String email = "test@example.com";
        String type = "login";

        // 发送验证码
        String code = verificationCodeService.sendVerificationCode(email, type);
        Assertions.assertNotNull(code);
        // 校验验证码（应为true）
        boolean result = verificationCodeService.verifyCode(email, code, type);
        Assertions.assertTrue(result);
        // 再次校验（应为false，已删除）
        boolean result2 = verificationCodeService.verifyCode(email, code, type);
        Assertions.assertFalse(result2);
        // 验证邮件服务被调用
        verify(emailService, atLeastOnce()).sendSimpleEmail(eq(email), contains("登录验证码"), anyString());
    }
}