package com.songheqing.microforum.vo;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ArticleListVO {
    @Schema(description = "ID,主键")
    private Long id;

    @Schema(description = "频道卡片")
    private ChannelCardVO channelCard;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "正文预览")
    private String contentPreview;

    @Schema(description = "文章封面图片URL")
    private List<ImageVO> coverImageUrl;

    @Schema(description = "点赞量")
    private Integer likeCount;

    @Schema(description = "回复（评论）量")
    private Integer commentCount;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}