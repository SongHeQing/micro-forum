package com.songheqing.microforum.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "频道详细信息VO")
public class ChannelVO {
    @Schema(description = "频道ID")
    private Long id;

    @Schema(description = "频道名称")
    private String channelname;

    @Schema(description = "频道封面图 URL")
    private String imageUrl;

    @Schema(description = "频道背景图 URL")
    private String backgroundUrl;

    @Schema(description = "主色调（HEX格式，如 #FF5733）")
    private String dominantColor;

    @Schema(description = "频道描述")
    private String description;

    @Schema(description = "频道详情")
    private String detail;

    @Schema(description = "用户数量（关注数）")
    private Long userCount;

    @Schema(description = "文章数量")
    private Long articleCount;
}