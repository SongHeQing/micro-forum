package com.songheqing.microforum.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户统计数据VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileVO {
    @Schema(description = "头像链接")
    private String image; // 头像链接
    @Schema(description = "昵称")
    private String nickname; // 昵称
    @Schema(description = "用户关注量")
    private Integer followCount; // 用户关注量
    @Schema(description = "粉丝数量")
    private Integer fansCount; // 粉丝数量
    @Schema(description = "文章发送量")
    private Integer articleSendCount; // 文章发送量
    @Schema(description = "评论发送量")
    private Integer commentSendCount; // 评论发送量
    @Schema(description = "频道关注量")
    private Integer channelFollowCount; // 频道关注量
}