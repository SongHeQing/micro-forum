package com.songheqing.microforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.songheqing.microforum.service.EmailService;
import lombok.extern.slf4j.Slf4j;

/**
 * 邮件服务实现类
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    // 注入邮件发送器
    @Autowired
    private JavaMailSender mailSender;

    // 发件人邮箱
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发送纯文本邮件
     * 
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        // 创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置发件人
        message.setFrom(fromEmail);
        // 设置收件人
        message.setTo(to);
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setText(text);
        // 发送邮件
        try {
            mailSender.send(message);
            log.info("Email sent successfully to: " + to);
        } catch (MailException e) {
            log.error("Error sending email to " + to + ": " + e.getMessage()); // 打印错误信息
            // 可以在这里记录日志或抛出自定义异常
            throw new RuntimeException("Failed to send email", e);
        }
    }

    // 你也可以扩展一个发送 HTML 邮件的方法
    /*
     * public void sendHtmlEmail(String to, String subject, String htmlContent)
     * throws MessagingException {
     * MimeMessage message = mailSender.createMimeMessage();
     * MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); //
     * true for multipart message
     * helper.setFrom(mailSender.getUsername());
     * helper.setTo(to);
     * helper.setSubject(subject);
     * helper.setText(htmlContent, true); // true indicates HTML content
     * try {
     * mailSender.send(message);
     * System.out.println("HTML Email sent successfully to: " + to);
     * } catch (MailException e) {
     * System.err.println("Error sending HTML email to " + to + ": " +
     * e.getMessage());
     * throw new RuntimeException("Failed to send HTML email", e);
     * }
     * }
     */
}