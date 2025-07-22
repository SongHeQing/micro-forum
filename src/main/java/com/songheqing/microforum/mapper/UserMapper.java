package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.songheqing.microforum.entity.User;

@Mapper
public interface UserMapper {

    User login(User user);

    Integer findByNickname(String nickname);

    Integer findByEmail(String email);

    void insert(User user);
}
