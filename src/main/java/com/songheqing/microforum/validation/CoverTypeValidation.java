package com.songheqing.microforum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * 封面类型验证注解
 */
@Documented
@Constraint(validatedBy = CoverTypeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CoverTypeValidation {
    String message() default "封面类型必须是0、1或2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}