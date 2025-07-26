package com.songheqing.microforum.request;

import com.songheqing.microforum.validation.UnicodeSizeValidation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "提交二级评论请求参数")
@Data
public class CommentReplyAddRequest {

    @Schema(description = "文章ID", example = "1")
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    @Schema(description = "父评论ID，即一级评论ID", example = "1")
    @NotNull(message = "父评论ID不能为空")
    private Long parentId;

    @Schema(description = "被回复用户ID（可选），用于 UI 展示“回复@XXX”", example = "3")
    private Long replyToUserId;

    @Schema(description = "回复内容，支持富文本，最大长度为500字", example = "你说得太棒了！")
    @NotEmpty(message = "回复内容不能为空")
    @UnicodeSizeValidation(min = 1, max = 500, message = "回复内容不能超过500字")
    private String content;
}
