package com.songheqing.microforum.service;

import com.songheqing.microforum.entity.User;
import com.songheqing.microforum.request.UserRegisterRequest;
import com.songheqing.microforum.vo.LoginInfo;

public interface UserService {
    LoginInfo login(User user);

    void register(UserRegisterRequest userRegisterRequest);

    void verifyRegisterCode(UserRegisterRequest userRegisterRequest, String code);
}
