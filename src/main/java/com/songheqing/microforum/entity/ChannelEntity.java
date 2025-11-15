package com.songheqing.microforum.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChannelEntity {
    private Long id;
    private Long creatorId;
    private String channelname;
    private String image;
    private String background;
    private String dominantColor;
    private String description;
    private String detail;
    private Long userCount;
    private Long articleCount;
    private LocalDateTime createTime;
}