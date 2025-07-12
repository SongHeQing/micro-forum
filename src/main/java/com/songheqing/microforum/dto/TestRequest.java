package com.songheqing.microforum.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 测试请求DTO - 用于验证默认校验错误响应
 */
@Data
public class TestRequest {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "年龄不能为空")
    private Integer age;
}