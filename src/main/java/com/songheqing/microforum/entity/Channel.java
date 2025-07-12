package com.songheqing.microforum.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Channel {
    private Integer id;
    private String channelname;
    private String image;
    private Integer userConut;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}