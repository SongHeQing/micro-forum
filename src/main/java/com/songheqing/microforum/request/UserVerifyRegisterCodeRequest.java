package com.songheqing.microforum.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * 用户验证注册码请求
 */
@Data
public class UserVerifyRegisterCodeRequest {

    /** 邮箱 */
    @NotNull(message = "邮箱不能为空") // @NotNull 报错： null；
    @Email(message = "邮箱格式不正确") // @Email 验证邮箱格式，空格是不合法的邮箱格式 不报错： null；
    private String email;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "(?=.*[a-z])" + // 至少一个小写字母
            "(?=.*[A-Z])" + // 至少一个大写字母
            "(?=.*\\d)" + // 至少一个数字
            "(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:'\"\\\\|,.<>/?~])" + // 至少一个特殊字符，包含 !@#$%^&*()_+-=[]{};:'"\\|,.<>/?~
            "(?=\\S+$)" + // 不能有空格
            "[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:'\"\\\\|,.<>/?~]{8,16}") // 密码长度8-16位，只能包含大小写字母、数字、特殊字符，不能有空格
    private String password;

    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "验证码长度6位，只能包含数字") // 验证码长度6位，只能包含数字
    private String code;
}
