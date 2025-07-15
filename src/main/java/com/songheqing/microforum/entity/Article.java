package com.songheqing.microforum.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
/**
 * 文章实体类
 */
public class Article {
    /** ID,主键 */
    private Integer id;

    /** 用户ID，关联用户表 */
    private Integer userId;

    /** 频道ID，关联频道表，表示文章所属的频道 */
    private Integer channelId;

    /** 标题 */
    private String title;

    /** 正文预览 */
    private String contentPreview;

    /** 完整的文章内容，最大2000字符 */
    private String content;

    /** 封面类型：null=无；1=图片；2=视频 */
    private Integer coverType;

    /** 创建时间（数据库自动填充） */
    private LocalDateTime createTime;

    /** 修改时间（数据库自动填充） */
    private LocalDateTime updateTime;
}
