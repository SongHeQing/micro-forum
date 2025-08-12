package com.songheqing.microforum.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String phone;
    private String email;
    private String password;
    private String nickname;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
