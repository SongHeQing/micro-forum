package com.songheqing.microforum.service;

public interface EmailService {

    void sendSimpleEmail(String to, String subject, String text);

}
