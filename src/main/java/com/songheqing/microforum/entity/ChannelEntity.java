package com.songheqing.microforum.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChannelEntity {
    private Integer id;
    private String channelname;
    private String image;
    private Integer userCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}