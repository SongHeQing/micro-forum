package com.songheqing.microforum.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后端统一返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code; // 编码：1成功，0为失败
    private String message; // 错误信息
    private T data; // 数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "success";
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = 200;
        result.message = "success";
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.message = message;
        result.code = 404;
        return result;
    }

    public static <T> Result<T> error(String code, String message) {
        Result<T> result = new Result<>();
        result.message = message;
        // 如果 code 是数字字符串，转换为整数；否则使用默认错误码
        try {
            result.code = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            result.code = 400; // 默认业务错误码
        }
        return result;
    }

}
