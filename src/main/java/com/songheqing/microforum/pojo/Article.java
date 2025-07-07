package com.songheqing.microforum.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;
    private Integer userId;
    private String title;
    private String contentPreview;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
