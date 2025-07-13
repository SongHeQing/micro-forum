package com.songheqing.microforum.exception;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.songheqing.microforum.vo.Result;
import com.songheqing.microforum.vo.ValidationErrorVO;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
@Slf4j
// @RestControllerAdvice(basePackages = "com.songheqing.microforum.controller")
@RestControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @Hidden
    public ResponseEntity<Result<Object>> handleException(Exception e) {
        log.error("操作失败：{}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("服务器内部发生未知错误，请联系管理员"));
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorVO> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        // 检查绑定结果是否存在
        if (e.getBindingResult() != null && e.getBindingResult().hasFieldErrors()) {
            // 收集所有校验错误
            for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                if (fieldError != null && fieldError.getField() != null) {
                    String fieldName = fieldError.getField();
                    String errorMessage = fieldError.getDefaultMessage();

                    // 如果错误信息为空，使用默认信息
                    if (errorMessage == null || errorMessage.trim().isEmpty()) {
                        errorMessage = "字段 " + fieldName + " 验证失败";
                    }

                    errors.put(fieldName, errorMessage);
                }
            }
        }

        // 记录详细的错误信息
        log.warn("参数校验失败，错误数量: {}, 错误详情: {}", errors.size(), errors);

        // 如果没有收集到错误信息，设置默认错误
        if (errors.isEmpty()) {
            errors.put("general", "参数校验失败");
        }

        ValidationErrorVO validationError = new ValidationErrorVO(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }
}
