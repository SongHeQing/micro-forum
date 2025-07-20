package com.songheqing.microforum.exception;

/**
 * 用户相关异常
 */
public class UserException extends BusinessException {

    public UserException(String message) {
        super("USER_ERROR", message);
    }

    public UserException(String message, Throwable cause) {
        super("USER_ERROR", message, cause);
    }

    // 用户已存在
    public static UserException userAlreadyExists() {
        return new UserException("用户已存在");
    }

    // 邮箱已存在
    public static UserException emailAlreadyExists() {
        return new UserException("邮箱已存在");
    }

    // 用户名或密码错误
    public static UserException invalidCredentials() {
        return new UserException("用户名或密码错误");
    }

    // 验证码错误
    public static UserException invalidVerificationCode() {
        return new UserException("验证码错误或已过期");
    }

    // 验证码发送失败
    public static UserException verificationCodeSendFailed(Throwable cause) {
        return new UserException("验证码发送失败", cause);
    }
}