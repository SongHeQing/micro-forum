package com.songheqing.microforum.request;

import com.songheqing.microforum.validation.UnicodeSizeValidation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "新增评论请求参数")
@Data
public class CommentAddRequest {
    @Schema(description = "文章ID", example = "1")
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    @Schema(description = "评论内容，最多2000字", example = "这是一条评论")
    @NotEmpty(message = "评论内容不能为空")
    @UnicodeSizeValidation(min = 1, max = 2000, message = "评论内容不能超过2000字")
    private String content;
}
