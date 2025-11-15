package com.songheqing.microforum.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChannelOwnerEntity {
    private Long id;
    private Long channelId;
    private Long userId;
    private Integer type; // 1:频道主, 2:频道管理员, 3:板块管理员
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}