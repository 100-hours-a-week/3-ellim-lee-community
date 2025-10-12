package gguip1.community.domain.user.dto;

import lombok.Data;

/**
 * 사용자 비밀번호 수정 요청 DTO입니다.
 * Fields:
 *  password - 사용자의 새 비밀번호
 *  password2 - 새 비밀번호 확인
 */
@Data
public class UserPasswordUpdateRequest {
    private String newPassword;
    private String newPassword2;
}
