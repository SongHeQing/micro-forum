package com.songheqing.microforum.vo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Integer id; // 用户ID
    private String username; // 用户名
    private String email; // 邮箱
    private String token; // 令牌
}