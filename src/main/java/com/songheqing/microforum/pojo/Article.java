package com.songheqing.microforum.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;
    private Integer userId;
    private Integer channelId;
    private String title;
    private String contentPreview;
    private String content;
    private Integer coverType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
