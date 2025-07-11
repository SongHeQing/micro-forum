package com.songheqing.microforum.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String phone;
    private String password;
    private String email;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
