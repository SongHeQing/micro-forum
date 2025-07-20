package com.songheqing.microforum.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 用户注册请求
 */
@Data
public class UserRegisterRequest {
    /** 邮箱 */
    @NotNull(message = "邮箱不能为空") // @NotNull 报错： null；
    @Email(message = "邮箱格式不正确") // @Email 验证邮箱格式，空格是不合法的邮箱格式 不报错： null；
    private String email;

    /** 密码 */
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "(?=.*[a-z])" + // 至少一个小写字母
            "(?=.*[A-Z])" + // 至少一个大写字母
            "(?=.*\\d)" + // 至少一个数字
            // "(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:'\"\\\\|,.<>/?~])" + // 至少一个特殊字符，包含
            // !@#$%^&*()_+-=[]{};:'"\\|,.<>/?~
            "(?=\\S+$)" + // 不能有空格
            "[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:'\"\\\\|,.<>/?~]{8,16}", // 密码长度8-16位，只能包含大小写字母、数字、特殊字符，不能有空格
            message = "密码必须包含大小写字母、数字，长度8-16位，不能有空格")
    private String password;

    // /** 昵称 */
    // @UserProfileNicknameValidation
    // private String nickname;
}
