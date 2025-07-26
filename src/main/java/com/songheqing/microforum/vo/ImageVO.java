package com.songheqing.microforum.vo;

import lombok.Data;

@Data
public class ImageVO {
    /** 图片URL */
    private String imageUrl;
    /** 图片排序 */
    private Integer orderNum;
}