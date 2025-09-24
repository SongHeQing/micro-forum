package com.songheqing.microforum.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserEntity {
    private Long id;
    private String phone;
    private String email;
    private String password;
    private String nickname;
    private String image;
    private Integer articleSendCount;
    private Integer commentSendCount;
    private Integer channelFollowCount;
    private Integer followCount;
    private Integer fansCount;
    private Integer likeCount;
    private String introduction;
    private LocalDateTime createTime;
}