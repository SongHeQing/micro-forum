package com.songheqing.microforum.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ArticleListDTO {
    /** ID,主键 */
    private Integer id;
    /** 用户ID，关联用户表 */
    private Integer userId;
    /** 频道ID，关联频道表，表示文章所属的频道 */
    private Integer channelId;
    /** 标题 */
    private String title;
    /** 正文预览 */
    private String contentPreview;
    /** 文章封面图片URL */
    private List<ImageDTO> coverImageUrl;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
}