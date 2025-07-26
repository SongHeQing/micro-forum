package com.songheqing.microforum.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.songheqing.microforum.entity.UserEntity;

@Mapper
public interface UserMapper {

    UserEntity login(UserEntity user);

    Integer findByNickname(String nickname);

    Integer findByEmail(String email);

    void insert(UserEntity user);
}
