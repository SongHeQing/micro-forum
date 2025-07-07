package com.songheqing.microforum.pojo;

import lombok.Data;

/**
 * 后端统一返回结果
 */
@Data
public class Result<T> {

    private Integer code; //编码：1成功，0为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> Result<T>  success() {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = "success";
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = 200;
        result.msg = "success";
        return result;
    }

    public static <T> Result<T>   error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 404;
        return result;
    }

}
