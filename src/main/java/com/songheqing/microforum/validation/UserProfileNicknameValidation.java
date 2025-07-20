package com.songheqing.microforum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@NotNull // null 报错
@Pattern(regexp = "^[\\p{L}\\p{N}_.-]+$") // 必须出现指定的字符，至少一个，以外的字符报错
@Constraint(validatedBy = { UserProfileNicknameValidator.class }) // 组合注解，并且绑定 Validator
@ReportAsSingleViolation // 只报告一个统一的错误消息
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserProfileNicknameValidation { // <-- 你选择的名称

    // 统一的错误消息
    String message() default "昵称不符合规范：不能为空，长度需在3到20个字符之间，且只能包含中文、字母、数字、下划线、连字符、点、括号。不允许空格。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 3;

    int max() default 20;
}