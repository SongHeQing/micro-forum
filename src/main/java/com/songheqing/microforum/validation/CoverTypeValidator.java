package com.songheqing.microforum.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoverTypeValidator implements ConstraintValidator<CoverTypeValidation, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 如果为空，验证通过
        if (value == null) {
            return true;
        }

        // 如果不为空，验证值是否合法
        return value == 0 || value == 1 || value == 2;
    }
}