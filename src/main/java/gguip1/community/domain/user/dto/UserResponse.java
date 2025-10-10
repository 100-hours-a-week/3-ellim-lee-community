package gguip1.community.domain.user.dto;

import lombok.Data;

/**
 * 사용자 응답 DTO입니다. (내 정보 조회 등)
 * Fields:
 *  email - 사용자의 이메일
 *  nickname - 사용자의 닉네임
 *  profileImageUrl - 프로필 이미지 URL
 */
@Data
public class UserResponse {
//    private Integer id;
    private String email;
    private String nickname;
    private String profileImageUrl;
}
