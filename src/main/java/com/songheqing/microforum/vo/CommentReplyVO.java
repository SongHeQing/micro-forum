package com.songheqing.microforum.vo;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentReplyVO {
    @Schema(description = "子评论 ID")
    private Long id;

    @Schema(description = "评论所属用户信息")
    private UserSimpleVO user;

    @Schema(description = "回复目标用户信息")
    private UserSimpleVO replyToUser;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
