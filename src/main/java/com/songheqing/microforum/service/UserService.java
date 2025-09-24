package com.songheqing.microforum.service;

import com.songheqing.microforum.entity.UserEntity;
import com.songheqing.microforum.request.UserRegisterRequest;
import com.songheqing.microforum.vo.LoginInfo;
import com.songheqing.microforum.vo.UserHomeVO;
import com.songheqing.microforum.vo.UserProfileVO;

public interface UserService {
    LoginInfo login(UserEntity user);

    void register(UserRegisterRequest userRegisterRequest);

    void verifyRegisterCode(UserRegisterRequest userRegisterRequest, String code);

    /**
     * 根据用户ID获取用户统计数据
     * 
     * @return 用户统计数据
     */
    UserProfileVO getUserProfileById();

    /**
     * 根据用户ID获取用户主页信息
     * 
     * @param userId 用户ID
     * @return 用户主页信息
     */
    UserHomeVO getUserHomeById(Long userId);
}
