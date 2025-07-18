package com.songheqing.microforum.utils;

import java.security.SecureRandom;

public class VerificationCodeGenerator {

    private static final String CHARACTERS = "0123456789"; // 纯数字验证码
    // private static final String CHARACTERS =
    // "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 字母数字混合
    private static final int CODE_LENGTH = 6; // 验证码长度
    private static final SecureRandom random = new SecureRandom();

    public static String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}