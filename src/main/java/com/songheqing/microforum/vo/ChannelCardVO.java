package com.songheqing.microforum.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class ChannelCardVO {

    @Schema(description = "频道ID")
    private Integer id;

    @Schema(description = "频道名称")
    private String channelName;

    @Schema(description = "用户数量（关注数）")
    private Integer userCount;

    @Schema(description = "文章数量")
    private Integer articleCount;

    @Schema(description = "频道封面图 URL")
    private String image;
}
