package com.songheqing.microforum.request;

import lombok.Data;

import com.songheqing.microforum.validation.UnicodeSizeValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 文章表单数据传输对象
 * 用于接收前端提交的文章创建/编辑表单数据
 */
@Data
public class PublishArticleRequest {

    /** 作者用户ID，必填 */
    @NotNull(message = "作者用户ID不能为空")
    private Integer userId;

    /** 频道ID，必填 */
    @NotNull(message = "频道ID不能为空")
    private Integer channelId;

    /** 文章标题，必填，长度限制 */
    @NotBlank(message = "文章标题不能为空")
    @UnicodeSizeValidation(min = 1, max = 31, message = "文章标题长度必须在1-31个字符之间")
    private String title;

    /** 文章完整内容，可选, 长度限制 */
    @UnicodeSizeValidation(min = 0, max = 3000, message = "文章内容长度必须在0-3000个字符之间")
    private String content;

    /** 封面类型：0-无封面，1-图片封面，2-视频封面等 */
    private Integer coverType = 0;
}