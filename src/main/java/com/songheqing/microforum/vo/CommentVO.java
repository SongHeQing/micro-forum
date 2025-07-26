package com.songheqing.microforum.vo;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentVO {
    @Schema(description = "评论 ID")
    private Long id;

    @Schema(description = "评论所属用户信息")
    private UserSimpleVO user;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "该评论下的子评论总数")
    private Integer replyCount;

    @Schema(description = "前 4 条子评论预览")
    private List<CommentReplyVO> previewReplies;

    @Schema(description = "楼层号")
    private Integer floor;
}