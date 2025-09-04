package com.songheqing.microforum.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentLikeEntity {
    /** 评论点赞记录ID */
    private Long id;

    /** 点赞用户ID */
    private Long userId;

    /** 所属文章ID（冗余，方便查询） */
    private Long articleId;

    /** 被点赞的评论ID */
    private Long commentId;

    /** 点赞时间 */
    private LocalDateTime createTime;
}
