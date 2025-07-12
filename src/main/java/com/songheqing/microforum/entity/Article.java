package com.songheqing.microforum.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
/**
 * 文章实体类
 */
public class Article {
    /** 文章ID，主键 */
    private Integer id;

    /** 作者用户ID，关联用户表 */
    private Integer userId;

    /** 频道ID，关联频道表，表示文章所属的频道 */
    private Integer channelId;

    /** 文章标题 */
    private String title;

    /** 文章内容预览，用于列表展示的简短描述 */
    private String contentPreview;

    /** 文章完整内容 */
    private String content;

    /** 封面类型：0-无封面，1-图片封面，2-视频封面等 */
    private Integer coverType;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
