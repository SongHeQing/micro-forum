package com.songheqing.microforum.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ImageEntity {
    /** 图片ID,主键 */
    private Long id;

    /** 图片的存储路径/URL */
    private String imageUrl;

    /** 关联的实体类型（例如 "ARTICLE", "USER", "CHANNEL", "COMMENT"） */
    private String entityType;

    /** 关联的业务ID（例如文章ID、用户ID、频道ID、评论ID） */
    private Long entityId;

    /** 图片排序（针对多图实体，如文章的图片列表顺序） */
    private Short orderNum;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}