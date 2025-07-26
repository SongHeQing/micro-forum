package com.songheqing.microforum.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
/**
 * 文章实体类
 */
public class ArticleEntity {
    /** ID,主键 */
    private Long id;

    /** 用户ID，关联用户表 */
    private Long userId;

    /** 频道ID，关联频道表，表示文章所属的频道 */
    private Integer channelId;

    /** 标题 */
    private String title;

    /** 正文预览 */
    private String contentPreview;

    /** 完整的文章内容，最大2000字符 */
    private String content;

    /** 封面类型：0=无；1=图片；2=视频 */
    private Integer coverType;

    /** 点赞量 */
    private Integer likeCount;

    /** 收藏量 */
    private Integer collectCount;

    /** 回复（评论）量 */
    private Integer commentCount;

    /** 文章点击量/阅读量 */
    private Integer viewCount;

    /** 文章的一级评论楼层计数 */
    private Integer floorCount;

    /** 创建时间（数据库自动填充） */
    private LocalDateTime createTime;

    /** 修改时间（数据库自动填充） */
    private LocalDateTime updateTime;
}
