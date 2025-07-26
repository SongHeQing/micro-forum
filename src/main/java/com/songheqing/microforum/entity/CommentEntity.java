package com.songheqing.microforum.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentEntity {
    /** 主键 ID */
    private Long id;

    /** 所属文章 ID */
    private Long articleId;

    /** 父评论 ID，一级评论为空 */
    private Long parentId;

    /** 楼层号，仅一级评论有值 */
    private Integer floor;

    /** 评论所属用户 ID */
    private Long userId;

    /** 二级评论数 */
    private Integer replyCount;

    /** 回复目标用户 ID（用于 UI 展示） */
    private Long replyToUserId;

    /** 评论内容 */
    private String content;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
