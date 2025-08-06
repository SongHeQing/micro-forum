package com.songheqing.microforum.vo;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ArticleDetailVO {
    @Schema(description = "ID,主键")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "正文")
    private String content;

    @Schema(description = "媒体类型：NULL=无；1=图片；2=视频")
    private Integer mediaType; // 统一字段名

    @Schema(description = "点赞量")
    private Integer likeCount;

    @Schema(description = "收藏量")
    private Integer collectCount;

    @Schema(description = "回复（评论）量")
    private Integer commentCount;

    @Schema(description = "浏览量")
    private Integer viewCount;

    @Schema(description = "媒体URLs")
    private List<String> mediaUrls;

    @Schema(description = "是否点赞")
    private Boolean isLiked; // 添加点赞状态字段

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "用户")
    private UserSimpleVO user;

    @Schema(description = "频道")
    private ChannelSimpleVO channel;
}