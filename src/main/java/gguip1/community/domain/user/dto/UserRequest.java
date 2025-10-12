package gguip1.community.domain.user.dto;

import lombok.Data;

/**
 * 회원가입 요청 DTO입니다.
 * Fields:
 *  email - 사용자의 이메일
 *  password - 사용자의 비밀번호
 *  password2 - 비밀번호 확인
 *  nickname - 사용자의 닉네임
 *  profileImageId - 프로필 이미지 ID (선택 사항)
 */
@Data
public class UserRequest {
    private String email;
    private String password;
    private String password2;
    private String nickname;
    private Long profileImageId;
}
