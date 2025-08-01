package com.songheqing.microforum.service.impl;

import com.songheqing.microforum.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 模拟邮件服务实现类 (开发环境)
 * 只有在激活'dev' profile时，此Bean才会被创建。
 */
@Service // 仍然是一个Spring Bean
@Profile({ "dev", "docker-dev" })
@Slf4j
public class MockEmailServiceImpl implements EmailService {

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        // 在开发环境，我们不真正发送邮件，只打印日志信息，模拟邮件发送过程
        log.warn("【开发环境】邮件服务被模拟，不会真实发送邮件。邮件详情：");
        log.warn("收件人: {}", to);
        log.warn("主题: {}", subject);
        log.warn("内容: {}", text);
    }
}