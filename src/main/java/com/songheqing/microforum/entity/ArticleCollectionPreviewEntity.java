package com.songheqing.microforum.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
/**
 * 文章收藏预览实体类
 */
public class ArticleCollectionPreviewEntity {
    /** 文章ID (与article.id相同，作为预览记录的主键) */
    private Long id;

    /** 文章作者用户ID */
    private Long authorUserId;

    /** 文章标题 */
    private String title;

    /** 文章第一张图片URL (封面图) */
    private String firstImage;

    /** 文章ID (与article.id相同) */
    private Long articleId;

    /** 被收藏的数量，用于管理本记录的生命周期 */
    private Integer collectedCount;

    /** 记录创建时间 (首次被收藏时) */
    private LocalDateTime createTime;
}
