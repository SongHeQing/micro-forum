package com.songheqing.microforum.request;

import lombok.Data;

import com.songheqing.microforum.validation.UnicodeSizeValidation;

import jakarta.validation.constraints.NotBlank;

/**
 * 频道请求DTO
 */
@Data
public class ChannelRequest {
    /**
     * 频道名称
     */
    @NotBlank(message = "频道名称不能为空")
    @UnicodeSizeValidation(min = 1, max = 14, message = "频道名称最多14个字符")
    private String channelname;

    /**
     * 频道简介 (最多20字符)
     */
    @UnicodeSizeValidation(max = 20, message = "频道简介最多20个字符")
    private String description;

    /**
     * 详细简介 (最多500字符)
     */
    @UnicodeSizeValidation(max = 500, message = "详细简介最多500个字符")
    private String detail;
}