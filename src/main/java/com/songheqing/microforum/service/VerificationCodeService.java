package com.songheqing.microforum.service;

public interface VerificationCodeService {

    String sendVerificationCode(String email, String type);

    boolean verifyCode(String email, String code, String type);

}
