package gguip1.community.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 로그인 요청 DTO입니다.
 * Fields:
 *  email - 사용자의 이메일
 *  password - 사용자의 비밀번호
 */
@Data
public class AuthRequest {
    @Email
    @NotBlank
    private String email;
    private String password;
}
