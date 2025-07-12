package com.songheqing.microforum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义Unicode字符长度验证注解
 * 按Unicode代码点数量验证字符串长度，而不是按代码单元数量
 */
@Documented
@Constraint(validatedBy = UnicodeSizeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UnicodeSizeValidation {

    String message() default "字符串长度必须在{min}到{max}个字符之间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}