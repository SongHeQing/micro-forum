package com.songheqing.microforum.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户主页信息VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHomeVO {
    @Schema(description = "头像链接")
    private String image;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "用户关注量")
    private Integer followCount;

    @Schema(description = "粉丝数量")
    private Integer fansCount;

    @Schema(description = "文章发送量")
    private Integer articleSendCount;

    @Schema(description = "评论发送量")
    private Integer commentSendCount;

    @Schema(description = "频道关注量")
    private Integer channelFollowCount;

    @Schema(description = "获赞量")
    private Integer likeCount;

    @Schema(description = "个人简介")
    private String introduction;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}