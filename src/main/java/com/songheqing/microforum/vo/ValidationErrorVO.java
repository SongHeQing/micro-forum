package com.songheqing.microforum.vo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * 校验错误响应VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorVO {

    /** 错误码 */
    private Integer code = 400;

    /** 错误消息 */
    private String message = "参数校验失败";

    /** 字段错误详情 */
    private Map<String, String> fieldErrors;

    /** 错误总数 */
    private Integer errorCount;

    public ValidationErrorVO(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
        this.errorCount = fieldErrors.size();
    }
}