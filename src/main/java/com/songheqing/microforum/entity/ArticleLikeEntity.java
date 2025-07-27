package com.songheqing.microforum.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleLikeEntity {
    private Long id;
    private Long userId;
    private Long articleId;
    private LocalDateTime createTime;
}
