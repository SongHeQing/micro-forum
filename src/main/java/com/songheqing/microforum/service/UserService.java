package com.songheqing.microforum.service;

import com.songheqing.microforum.entity.UserEntity;
import com.songheqing.microforum.request.UserRegisterRequest;
import com.songheqing.microforum.vo.LoginInfo;

public interface UserService {
    LoginInfo login(UserEntity user);

    void register(UserRegisterRequest userRegisterRequest);

    void verifyRegisterCode(UserRegisterRequest userRegisterRequest, String code);
}
