package com.songheqing.microforum.dto;

import lombok.Data;

@Data
public class ImageDTO {
    /** 图片URL */
    private String imageUrl;
    /** 图片排序 */
    private Integer orderNum;
}