package com.songheqing.microforum.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Unicode字符长度验证器实现
 */
public class UnicodeSizeValidator implements ConstraintValidator<UnicodeSizeValidation, String> {

    private int min;
    private int max;

    @Override
    public void initialize(UnicodeSizeValidation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果值为null，返回true，让@NotNull处理null值
        // 这样@UnicodeSizeValidation只负责长度验证，@NotNull负责null值验证
        if (value == null) {
            return true;
        }

        // 清除空白字符
        value = value.trim();

        // 计算Unicode代码点数量
        int codePointCount = value.codePointCount(0, value.length());

        // 验证长度范围
        return codePointCount >= min && codePointCount <= max;
    }
}