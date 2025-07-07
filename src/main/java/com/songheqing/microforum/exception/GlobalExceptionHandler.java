package com.songheqing.microforum.exception;

import com.songheqing.microforum.pojo.Result;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
//@RestControllerAdvice(basePackages = "com.songheqing.microforum.controller")
@RestControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler
    @Hidden
    public Result<Object> handleException(Exception e){
        log.error("操作失败：{}",e.getMessage());
        return Result.error("操作失败");
    }


}
